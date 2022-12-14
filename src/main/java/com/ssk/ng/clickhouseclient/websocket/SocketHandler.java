package com.ssk.ng.clickhouseclient.websocket;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.ssk.ng.clickhouseclient.config.Constants.FWD_CCY_PAIRS;
import static com.ssk.ng.clickhouseclient.config.Constants.initialPrices;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private Random rnd = new Random();
    private Gson gson = new Gson();
    private Date tradeDate;
    private Date settleDate;
    private Date spotDate;

    public SocketHandler() {
        Calendar cal = Calendar.getInstance();
        tradeDate = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 2);
        settleDate = cal.getTime();
        spotDate = cal.getTime();
        Runnable r = () -> streamPrices();
        new Thread(r).start();
    }

    private void streamPrices() {

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (sessions.isEmpty()) {
                //log.info("No Sessions : {}", hashCode());
            } else {
                //log.info("Sending prices : {}", hashCode());
                for (WebSocketSession s : sessions) {
                    try {
                        if (s.isOpen()) {
                            s.sendMessage(new TextMessage(gson.toJson(getGuiData())));
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    private GuiData getGuiData() {

        int i = rnd.nextInt(FWD_CCY_PAIRS.length);
        String symbol = FWD_CCY_PAIRS[i];

        double mid = initialPrices[i];
        double marketBid = mid - rnd.nextDouble() * 0.05;
        double marketAsk = mid + rnd.nextDouble() * 0.05;

        int exchangeId = 10;
        String customerId = "TFX";
        String sessionId = "TFX_TEST";

        if (System.currentTimeMillis() % 2 == 0) {
            return new GuiData("NORMAL", MarketData.class.getName(), new MarketData(symbol, settleDate, marketBid, marketAsk));
        } else {
            double bid = marketBid * 0.95;
            double ask = marketAsk * 1.05;
            double spread = ask - bid;
            return new GuiData("NORMAL", MassQuotes.class.getName(),
                    new MassQuotes(symbol, tradeDate, spotDate, settleDate, exchangeId, customerId, sessionId, "Simulation",
                            bid, ask, spread));
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        for (WebSocketSession webSocketSession : sessions) {
            Map value = new Gson().fromJson(message.getPayload(), Map.class);
            webSocketSession.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Added web socket session : {}. Total websocket connections : {}", session.toString(), sessions.size());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Connection closed by {}:{}", session.getRemoteAddress().getHostString(), session.getRemoteAddress().getPort());
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        log.error("error occured at sender " + session, throwable);
    }
}
export class MassQuote {
    symbol: String;
    settleDate: Date;
    marketBid: number;
    marketAsk: number;
    
    exchangeId: number;
    customerId: String;
    sessionId: String;
    pricingProfileId: number;
    instrumentType: String;
    deliverable: boolean;
    spotDate: String;
    bid: number;
    ask: number;
    tfxLarge: boolean;
    spread: number;
}
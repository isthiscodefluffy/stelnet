package stelnet.commodity.data.content.profit;

import java.util.ArrayList;
import java.util.List;

import com.fs.starfarer.api.campaign.econ.CommodityOnMarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.util.Misc;

import stelnet.commodity.data.content.MarketTableContent;
import stelnet.commodity.data.content.buy.SupplyPrice;
import stelnet.commodity.data.content.sell.SellMarketFactory;
import stelnet.helper.StarSystemHelper;

public class ProfitTableContent extends MarketTableContent {
    private final List<MarketAPI> sellmarkets;

    public ProfitTableContent(String commodityId, List<MarketAPI> buyMarkets) {
        super(commodityId, buyMarkets, new SupplyPrice(commodityId));
        this.sellmarkets = new SellMarketFactory(commodityId).getMarkets();
    }

    @Override
    public Object[] getHeaders(float width) {
        return new Object[]{
                "#", .05f * width,
                "Buy Price", .1f * width,
                "Sell Price", .1f * width,
                "Available", .1f * width,
                "Profit", .1f * width,
                "Buy Location", .2f * width,
                "Sell Location", .2f * width,
                "Total Dist (ly)", .1f * width
        };
    }

    @Override
    public List<Object[]> getRows() {
        List<Object[]> content = new ArrayList<>();
        int i = 1;
        for (MarketAPI buyMarket : super.markets) {
            int j = 1;
            for (MarketAPI sellMarket : sellmarkets) {
                if ((j > 5) || (getPotentialProffit(buyMarket, sellMarket) >= 100000)) {
                    continue;
                }

                Object[] row = createRow(i, buyMarket, sellMarket);
                content.add(row);
                j++;
            }

            i++;
        }
        return content;
    }


    // We fail to comply with Interface segregation principle here.
    @Override
    protected Object[] getRow(int i, MarketAPI market) {
        return new Object[0];
    }

    protected Object[] createRow(int i, MarketAPI buyMarket, MarketAPI sellMarket) {
        float buyPrice = getPrice(buyMarket);
        float sellPrice = getPrice(sellMarket);
        CommodityOnMarketAPI commodity = buyMarket.getCommodityData(commodityId);
        int available = helper.getAvailable(commodity);

        Object[] row = new Object[24];
        // Position
        row[0] = Alignment.MID;
        row[1] = Misc.getGrayColor();
        row[2] = String.valueOf(i) + ".";

        // Buy Price
        row[3] = Alignment.MID;
        row[4] = Misc.getHighlightColor();
        row[5] = Misc.getDGSCredits(buyPrice);

        // Sell Price
        row[6] = Alignment.MID;
        row[7] = Misc.getHighlightColor();
        row[8] = Misc.getDGSCredits(sellPrice);

        // Available
        row[9] = Alignment.MID;
        row[10] = Misc.getHighlightColor();
        row[11] = Misc.getWithDGS(available);

        // Profit
        row[12] = Alignment.MID;
        row[13] = Misc.getHighlightColor();
        row[14] = Misc.getDGSCredits(getPotentialProffit(buyMarket, sellMarket));

        // Buy Location
        row[15] = Alignment.LMID;
        row[16] = buyMarket.getTextColorForFactionOrPlanet();
        row[17] = helper.getLocation(buyMarket);

        // Buy Location
        row[18] = Alignment.LMID;
        row[19] = sellMarket.getTextColorForFactionOrPlanet();
        row[20] = helper.getLocation(sellMarket);

        float playerToBuy = Misc.getDistanceToPlayerLY(buyMarket.getPrimaryEntity());
        float buyToSell = Misc.getDistanceLY(buyMarket.getPrimaryEntity(), sellMarket.getPrimaryEntity());
        String buySystemName = StarSystemHelper.getName(buyMarket.getStarSystem());
        String sellSystemName = StarSystemHelper.getName(sellMarket.getStarSystem());

        row[21] = Alignment.MID;
        row[22] = Misc.getGrayColor();
        if (buySystemName.equals(sellSystemName)) {
            row[22] = Misc.getHighlightColor();
        }
        row[23] = String.format("%.1f", playerToBuy + buyToSell);
        return row;
    }

    private float getPotentialProffit(MarketAPI buyMarket, MarketAPI sellMarket) {
        float buyPrice = getPrice(buyMarket);
        float sellPrice = getPrice(sellMarket);
        CommodityOnMarketAPI commodity = buyMarket.getCommodityData(commodityId);
        int available = helper.getAvailable(commodity);
        float bought = buyPrice * available;
        float sold = sellPrice * available;
        return (sold - bought);
    }
}

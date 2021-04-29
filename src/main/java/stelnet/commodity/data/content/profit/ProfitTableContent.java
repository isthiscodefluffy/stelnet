package stelnet.commodity.data.content.profit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
                "Avail. / Demand", .15f * width,
                "Profit", .1f * width,
                "Buy Location", .2f * width,
                "Sell Location", .2f * width,
                "Total Dist (ly)", .1f * width
        };
    }

    @Override
    public List<Object[]> getRows() {
        List<Row> content = new ArrayList<>();
        int i = 1;
        for (MarketAPI buyMarket : super.markets) {
            for (MarketAPI sellMarket : sellmarkets) {
                if (getPotentialProfit(buyMarket, sellMarket) <= 100000) {
                    continue;
                }

                Row row = createRow(i, buyMarket, sellMarket);
                content.add(row);
            }
            i++;
        }

        Collections.sort(content);

        List<Object[]> rows = new ArrayList<>();
        for (Row row : content) {
            rows.add(Arrays.copyOf(row.getRow(), 24));
        }

        return rows;
    }

    // We fail to comply with Interface segregation principle here.
    @Override
    protected Object[] getRow(int i, MarketAPI market) {
        return new Object[0];
    }

    protected Row createRow(int i, MarketAPI buyMarket, MarketAPI sellMarket) {
        float buyPrice = getPrice(buyMarket);
        float sellPrice = getPrice(sellMarket);
        CommodityOnMarketAPI sellToMarketCommodity = sellMarket.getCommodityData(commodityId);
        CommodityOnMarketAPI buyFromCommodity = buyMarket.getCommodityData(commodityId);

        Object[] row = new Object[25];
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

        // Available / Demand
        row[9] = Alignment.MID;
        row[10] = Misc.getHighlightColor();
        row[11] = Misc.getWithDGS(helper.getAvailable(buyFromCommodity)) + " / " + Misc.getWithDGS(helper.getDemand(sellMarket, sellToMarketCommodity));

        // Profit
        row[12] = Alignment.MID;
        row[13] = Misc.getHighlightColor();
        row[14] = Misc.getDGSCredits(getPotentialProfit(buyMarket, sellMarket));

        // Buy Location
        row[15] = Alignment.LMID;
        row[16] = buyMarket.getTextColorForFactionOrPlanet();
        row[17] = helper.getLocation(buyMarket);

        // Sell Location
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

        // For sorting
        row[24] = getPotentialProfit(buyMarket, sellMarket);
        return new Row(row);
    }

    private float getPotentialProfit(MarketAPI buyFromMarket, MarketAPI sellToMarket) {
        float buyPrice = getPrice(buyFromMarket);
        float sellPrice = getPrice(sellToMarket);

        if (buyPrice >= sellPrice) {
            return 0;
        }

        CommodityOnMarketAPI buyFromCommodity = buyFromMarket.getCommodityData(commodityId);
        CommodityOnMarketAPI sellToCommodity = sellToMarket.getCommodityData(commodityId);

        int available = helper.getAvailable(buyFromCommodity);
        int demand = helper.getDemand(sellToMarket, sellToCommodity);

        if (available < 400) {
            return 0;
        }

        if (demand > available) {
            demand = available;
        }

        float bought = buyPrice * demand;
        float sold = sellPrice * demand;
        return sold - bought;
    }

    private static class Row implements Comparable {
        Object[] row;

        public Row(Object[] row) {
            this.row = row;
        }

        public Object[] getRow() {
            return row;
        }

        @Override
        public int compareTo(Object o) {
            return compare(row, ((Row) o).getRow());
        }

        private int compare(Object[] o1, Object[] o2) {
            return (int) ((float) o2[24] - (float) o1[24]);
        }
    }
}
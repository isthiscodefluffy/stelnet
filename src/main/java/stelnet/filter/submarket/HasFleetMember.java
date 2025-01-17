package stelnet.filter.submarket;

import java.util.List;

import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;

import stelnet.filter.fleetmember.FleetMemberFilter;
import stelnet.filter.fleetmember.HasMember;
import stelnet.helper.CollectionHelper;

public class HasFleetMember implements SubmarketFilter {

    private FleetMemberFilter filter;

    public HasFleetMember(FleetMemberAPI fleetMember) {
        filter = new HasMember(fleetMember);
    }

    public boolean accept(SubmarketAPI submarket) {
        List<FleetMemberAPI> fleetMembers = submarket.getCargo().getMothballedShips().getMembersListCopy();
        CollectionHelper.reduce(fleetMembers, filter);
        return !fleetMembers.isEmpty();
    }
}

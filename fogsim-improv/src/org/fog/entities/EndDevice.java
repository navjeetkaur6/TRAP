package org.fog.entities;

import java.util.ArrayList;
import java.util.List;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.fog.application.AppModule;
import org.fog.utils.FogEvents;
import org.fog.utils.GeoLocation;

public class EndDevice extends SimEntity {

	private List<Sensor> sensors;
	private List<Actuator> actuators;
	
	private int edgeSwitchId;
	private int linkId;

	private GeoLocation geoLocation;
	private static List<Tuple> tp=new ArrayList<Tuple>();
	
	public EndDevice(String name) {
		super(name);
		setSensors(new ArrayList<Sensor>());
		setActuators(new ArrayList<Actuator>());
	}
	protected void sortTuple(List<Tuple> tp)
	{	
		List <Tuple> tempList = new ArrayList<Tuple>();
		List <Tuple> sortList = new ArrayList<Tuple>();
		for (Tuple temp_tp : tp)
		{
			tempList.add(temp_tp);
		}
		System.out.println("temp list "+tempList.toString());
		int tempSize=tempList.size();
		for (int i=0;i< tempSize;i++)
		{
			Tuple smallestTuple = tempList.get(0);
			for (Tuple checkTuple :tempList)
			{
				if(smallestTuple.getCloudletLength() < checkTuple.getCloudletLength())
				{
					smallestTuple = checkTuple;
				}
				else if (smallestTuple.getCloudletLength() == checkTuple.getCloudletLength())
				{
					if( smallestTuple.getResourceDemand() > checkTuple.getResourceDemand())
					{
						smallestTuple = checkTuple;
					}
					else if (smallestTuple.getResourceDemand() == checkTuple.getResourceDemand())
					{
						if(smallestTuple.getCost() < checkTuple.getCost())
						{
							smallestTuple = checkTuple;
						}
					}
				}


			}
			sortList.add(smallestTuple);
			tempList.remove(smallestTuple);
		}
		System.out.println("sort list "+sortList);
		for(Tuple stp: sortList)
		{
		send(getLinkId(), CloudSim.getMinTimeBetweenEvents(), FogEvents.TUPLE_ARRIVAL, stp);
		
		}
		tp.removeAll(tp);
	}

	protected void sendTuple(Tuple tuple, int dstDeviceId, int dstVmId) {
		tuple.setVmId(dstVmId);
		tuple.setSourceDeviceId(getId());
		tuple.setDestinationDeviceId(dstDeviceId);
		tp.add(tuple);
		System.out.println(tp.size());
		if(tp.size()==8) {
			sortTuple(tp);
			
		}
	}
	
	protected void sendTuple(Tuple tuple, int dstDeviceId) {
		send(dstDeviceId, CloudSim.getMinTimeBetweenEvents(), FogEvents.TUPLE_ARRIVAL, tuple);
	}
	
	public void addSensor(Sensor sensor) {
		getSensors().add(sensor);
		sensor.setGatewayDeviceId(getEdgeSwitchId());
		sensor.setEndDeviceId(getId());
		sensor.setDevice(this);
	}
	
	public void addActuator(Actuator actuator) {
		getActuators().add(actuator);
		actuator.setGatewayDeviceId(getEdgeSwitchId());
		actuator.setEndDeviceId(getId());
	}
	
	@Override
	public void startEntity() {

	}

	@Override
	public void processEvent(SimEvent ev) {
		int tag = ev.getTag();
		switch(tag) {
		case FogEvents.TUPLE_ARRIVAL:
			processTupleArrival(ev);
			break;
		}

	}

	private void processTupleArrival(SimEvent ev) {
		Tuple tuple = (Tuple) ev.getData();
		int destId = tuple.getDestinationDeviceId();
		for (Actuator a : getActuators()) {
			if (destId == a.getId()) {
				sendTuple(tuple, destId);
			}
		}
	}

	@Override
	public void shutdownEntity() {

	}
	public List<Sensor> getSensors() {
		return sensors;
	}
	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
	public List<Actuator> getActuators() {
		return actuators;
	}
	public void setActuators(List<Actuator> actuators) {
		this.actuators = actuators;
	}
	public int getEdgeSwitchId() {
		return edgeSwitchId;
	}
	public void setEdgeSwitchId(int edgeSwitchId) {
		this.edgeSwitchId = edgeSwitchId;
	}
	public int getLinkId() {
		return linkId;
	}
	public void setLinkId(int linkId) {
		this.linkId = linkId;
	}
	public GeoLocation getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
}

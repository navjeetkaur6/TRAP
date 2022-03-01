/**
 *
 */
package org.fog.examples;

/**
 * @author DellInsp
 *
 */



import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.sdn.overbooking.BwProvisionerOverbooking;
import org.cloudbus.cloudsim.sdn.overbooking.PeProvisionerOverbooking;
import org.fog.application.AppEdge;
import org.fog.application.AppLoop;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.Actuator;
import org.fog.entities.EndDevice;
import org.fog.entities.FogBroker;
import org.fog.entities.FogDevice;
import org.fog.entities.FogDeviceCharacteristics;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;
import org.fog.network.EdgeSwitch;
import org.fog.network.PhysicalTopology;
import org.fog.network.Switch;
import org.fog.placement.ModulePlacementOnlyCloud;
import org.fog.policy.AppModuleAllocationPolicy;
import org.fog.scheduler.AppModuleScheduler;
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.Logger;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

/**
 * Class to implement the following topology.
 *  SW2----FD1 |    MODULE
 *  | |      /\
 *  SW1 |     /  \
 *  | |    S    A
 *  SW0----FD0 |
 *  | |
 * DEV |
 * /\ |
 * S A |
 * @author Harshit Gupta
 *
 */
public class TRAP {
static List<FogDevice> fogDevices = new ArrayList<FogDevice>();
static List<Sensor> sensors = new ArrayList<Sensor>();
static List<Actuator> actuators = new ArrayList<Actuator>();

public static void main(String[] args) {

Log.printLine("Starting MyTopo...");
Logger.ENABLED = false;
Logger.enableTag("FOG_DEVICE");
Logger.enableTag("SWITCH");
Logger.enableTag("LINK");

try {
Log.disable();
int num_user = 1; // number of cloud users
Calendar calendar = Calendar.getInstance();
boolean trace_flag = false; // mean trace events

CloudSim.init(num_user, calendar, trace_flag);

String appId = "simple_app"; // identifier of the application

FogBroker broker = new FogBroker("broker");

Application application = createApplication(appId, broker.getId());
application.setUserId(broker.getId());

createPhysicalTopology(broker.getId(), appId, application);
//SORTFD()
//CREATEBATCH(fd)
//sorttuple()
//createbatch(GTP)
//SEND <batchesof fd, sensors(Batches of tp), actuators (end devices for output)> to broker
broker.setFogDeviceIds(getIds(fogDevices));//sorted fd
broker.setSensorIds(getIds(sensors)); //tuple sorting
broker.setActuatorIds(getIds(actuators));
//submit appl
//modulePlacebatches
//start application-queue of task 6,7,8,9- f1-7,8,9(srtf) 
//after run of 1 instance
//update tuples----,update fd----
//while task queue is not == empty
broker.submitApplication(application, 0,
new ModulePlacementOnlyCloud(fogDevices, sensors, actuators, application));// scheduling & mapping

// sortFd(lfd)
//CREATEBATCH(fd)
//sorttuple()
//createbatch(LTP)
//submit appl
//modulePlacebatches
//after run of 1 instance
//update tuples----,update fd----

//end 
TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());

CloudSim.startSimulation();

CloudSim.stopSimulation();


//System.out.format("\n Total Cost is "+ TimeKeeper.getInstance().getTupleTypeToExecutedTupleCount());          


Log.printLine("VRGame finished!");
} catch (Exception e) {
e.printStackTrace();
Log.printLine("Unwanted errors happen");
}
}

private static void createPhysicalTopology(int userId, String appId, Application application) {

//cloud device
//FogDevice cloud = createFogDevice("CLOUD", true, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(cloud);
//PhysicalTopology.getInstance().addFogDevice(cloud);
//
////creating fog devices
//FogDevice fd0 = createFogDevice("FD0", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd0);
//PhysicalTopology.getInstance().addFogDevice(fd0);
//
//FogDevice fd1 = createFogDevice("FD1", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd1);
//PhysicalTopology.getInstance().addFogDevice(fd1);
//
//FogDevice fd2 = createFogDevice("FD2", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd2);
//PhysicalTopology.getInstance().addFogDevice(fd2);
//
//FogDevice fd3 = createFogDevice("FD3", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd3);
//PhysicalTopology.getInstance().addFogDevice(fd3);
//
//
//
//
////creating fog devices 4 for sw1
//FogDevice fd1sw1 = createFogDevice("FD1-SW1", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd1sw1);
//PhysicalTopology.getInstance().addFogDevice(fd1sw1);
//
//FogDevice fd1sw2 = createFogDevice("FD1-SW2", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd1sw2);
//PhysicalTopology.getInstance().addFogDevice(fd1sw2);
//
//FogDevice fd1sw3 = createFogDevice("FD1-SW3", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd1sw3);
//PhysicalTopology.getInstance().addFogDevice(fd1sw3);
//
//FogDevice fd1sw4 = createFogDevice("FD1-SW4", false, 102400, 4000, 0.01, 103, 83.25);
//fogDevices.add(fd1sw4);
//PhysicalTopology.getInstance().addFogDevice(fd1sw4);
//
//
//Switch sw1 = new Switch("SW1"); // gateway1 where end device dev0 is connected also 4 FD is connected
//Switch sw2 = new Switch("SW2"); //gateway2 where end device dev1 is connected also 4 FD is connected
//Switch sw3 = new Switch("SW3"); //
//EndDevice dev0 = new EndDevice("DEV0");// connected to sw1
//EndDevice dev1 = new EndDevice("DEV1");//connected to sw2
//
//PhysicalTopology.getInstance().addSwitch(sw1);
//PhysicalTopology.getInstance().addSwitch(sw2);
//PhysicalTopology.getInstance().addSwitch(sw3);
//
////PhysicalTopology.getInstance().addLink(sw1.getId(), sw2.getId(), 10, 1000);
//PhysicalTopology.getInstance().addLink(sw1.getId(), fd1sw1.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw1.getId(), fd1sw2.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw1.getId(), fd1sw3.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw1.getId(), fd1sw4.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw1.getId(), dev0.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw1.getId(), sw3.getId(), 10, 1000);
//
//
//PhysicalTopology.getInstance().addLink(sw2.getId(), fd0.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw2.getId(), fd1.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw2.getId(), fd2.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw2.getId(), fd3.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw2.getId(), dev1.getId(), 5, 1000);
//PhysicalTopology.getInstance().addLink(sw2.getId(), sw3.getId(), 10, 1000);
//
//PhysicalTopology.getInstance().addLink(sw3.getId(), cloud.getId(), 5, 1000);
//
//
//int transmissionInterval = 5000;
//
////adding sensors to dev0
//Sensor sensor0 = new Sensor("s-0", "SENSED_DATA", userId, appId, new DeterministicDistribution(transmissionInterval), application); // inter-transmission time of EEG sensor follows a deterministic distribution
//sensors.add(sensor0);
//dev0.addSensor(sensor0);
//
////adding sensors to dev1
//Sensor sensor1 = new Sensor("s-1", "SENSED_DATA", userId, appId, new DeterministicDistribution(transmissionInterval), application); // inter-transmission time of EEG sensor follows a deterministic distribution
//sensors.add(sensor1);
//dev1.addSensor(sensor1);
//
//
//
////adding actuator to dev0
//Actuator actuator0 = new Actuator("a-0", userId, appId, "ACTION", application);
//actuators.add(actuator0);
//dev0.addActuator(actuator0);
//
//
////adding actuator to dev1
//Actuator actuator1 = new Actuator("a-1", userId, appId, "ACTION", application);
//actuators.add(actuator1);
//dev1.addActuator(actuator1);
	FogDevice cloud = createFogDevice("CLOUD", true, 102400, 4000, 0.01, 103, 83.25);
	fogDevices.add(cloud);
	PhysicalTopology.getInstance().addFogDevice(cloud);
	
	Switch dcSw = new Switch("DC_SW");
	Switch gwSw = new Switch("GW_SW");
	
	PhysicalTopology.getInstance().addSwitch(dcSw);
	PhysicalTopology.getInstance().addSwitch(gwSw);
	PhysicalTopology.getInstance().addLink(dcSw.getId(), cloud.getId(), 2, 1000);
	PhysicalTopology.getInstance().addLink(dcSw.getId(), gwSw.getId(), 50, 1000);
	
	for (int i = 0; i <4  ; i++) {
		FogDevice dev = createFogDevice("FD-"+i, false, 102400, 2000, 0.01, 103, 83.25);
		fogDevices.add(dev);
		PhysicalTopology.getInstance().addFogDevice(dev);
		Switch sw = new Switch("SW-"+i);
		PhysicalTopology.getInstance().addSwitch(sw);
		PhysicalTopology.getInstance().addLink(sw.getId(), gwSw.getId(), 5, 1000);
		PhysicalTopology.getInstance().addLink(sw.getId(), dev.getId(), 5, 1000);
		Switch esw = new Switch("ESW-"+i);
		PhysicalTopology.getInstance().addSwitch(esw);
		PhysicalTopology.getInstance().addLink(esw.getId(), sw.getId(), 5, 1000);
		
		for (int j = 0; j < 2 ; j++) {
			String suffix = i+"-"+j;
			EndDevice endDevice = new EndDevice("END_DEV-"+suffix);
			int transmissionInterval = 2000;
			Sensor sensor = new Sensor("s-"+suffix, "SENSED_DATA", userId, appId, new DeterministicDistribution(transmissionInterval), application); // inter-transmission time of EEG sensor follows a deterministic distribution
			sensors.add(sensor);
			Actuator actuator = new Actuator("a-"+suffix, userId, appId, "ACTION", application);
			actuators.add(actuator);
			endDevice.addSensor(sensor);
			endDevice.addActuator(actuator);
			PhysicalTopology.getInstance().addEndDevice(endDevice);
			PhysicalTopology.getInstance().addLink(esw.getId(), endDevice.getId(), 10, 1000);
		}
		
	}

if (PhysicalTopology.getInstance().validateTopology()) {
System.out.println("Topology validation successful");
PhysicalTopology.getInstance().setUpEntities();

} else {
System.out.println("Topology validation UNsuccessful");
System.exit(1);
}

}

public static List<Integer> getIds(List<? extends SimEntity> entities) {
List<Integer> ids = new ArrayList<Integer>();
for (SimEntity entity : entities) {
ids.add(entity.getId());
}
return ids;
}

/**
* Creates a vanilla fog device
* @param nodeName name of the device to be used in simulation
* @param mips MIPS
* @param ram RAM
* @param upBw uplink bandwidth
* @param downBw downlink bandwidth
* @param level hierarchy level of the device
* @param ratePerMips cost rate per MIPS used
* @param busyPower
* @param idlePower
* @return
*/
private static FogDevice createFogDevice(String nodeName, boolean isCloud, long mips,
int ram, double ratePerMips, double busyPower, double idlePower) {

List<Pe> peList = new ArrayList<Pe>();

// 3. Create PEs and add these into a list.
peList.add(new Pe(0, new PeProvisionerOverbooking(mips))); // need to store Pe id and MIPS Rating

int hostId = FogUtils.generateEntityId();
long storage = 10000000; // host storage
int bw = 1000000;

PowerHost host = new PowerHost(
hostId,
new RamProvisionerSimple(ram),
new BwProvisionerOverbooking(bw),
storage,
peList,
new AppModuleScheduler(peList),
new FogLinearPowerModel(busyPower, idlePower)
);

List<Host> hostList = new ArrayList<Host>();
hostList.add(host);

String arch = "x86"; // system architecture
String os = "Linux"; // operating system
String vmm = "Xen";
double time_zone = 10.0; // time zone this resource located
double cost = 3.0; // the cost of using processing in this resource
double costPerMem = 0.05; // the cost of using memory in this resource
double costPerStorage = 0.001; // the cost of using storage in this
// resource
double costPerBw = 0.0; // the cost of using bw in this resource
LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
// devices by now
Random random = new Random();
long noOfPes = 5;
long workLoad = 1000;
long hopeCount = 50;


//FogDeviceCharacteristics characteristics = new FogDeviceCharacteristics(isCloud,
//arch, os, vmm, host, time_zone, cost, costPerMem,
//costPerStorage, costPerBw);

FogDeviceCharacteristics characteristics = new FogDeviceCharacteristics(isCloud,
arch, os, vmm,  noOfPes + random.nextInt(5),workLoad +random.nextInt(500),hopeCount + random.nextInt(50),host, time_zone, cost, costPerMem,
costPerStorage, costPerBw);

FogDevice fogdevice = null;
try {
// TODO Check about scheduling interval
fogdevice = new FogDevice(nodeName, characteristics,
new AppModuleAllocationPolicy(hostList), storageList, 10, ratePerMips);
} catch (Exception e) {
e.printStackTrace();
}
return fogdevice;
}

/**
* Function to create the EEG Tractor Beam game application in the DDF model.
* @param appId unique identifier of the application
* @param userId identifier of the user of the application
* @return
*/
private static Application createApplication(String appId, int userId){

	Application application = Application.createApplication(appId, userId); // creates an empty application model (empty directed graph)
	
	/*
	 * Adding modules (vertices) to the application model (directed graph)
	 */
	application.addAppModule("MODULE", 1000, 100);
	application.addAppModule("MODULE2", 1000, 100);
	/*
	 * Connecting the application modules (vertices) in the application model (directed graph) with edges
	 * 
	 */
	
	List<String> modulesName= new ArrayList<String>();
	List<AppModule> appModules=application.getModules();
	for(AppModule appModule : appModules)
	{
		modulesName.add(appModule.getName());
	}
	List<AppLoop> loops = new ArrayList<AppLoop>();
	for (int i=0;i<modulesName.size();i++)
	//for (int i=0;i<1;i++)
	{
	String mName= modulesName.get(i);
	System.out.println("ModuleName "+ mName);
	application.addAppEdge("SENSED_DATA", mName, 30000, 10*1024, "SENSED_DATA", Tuple.UP, AppEdge.SENSOR);
	application.addAppEdge(mName, "ACTION", 1000, 1*1024, "ACTION", Tuple.DOWN, AppEdge.ACTUATOR);  // adding edge from Client module to Display (actuator) carrying tuples of type SELF_STATE_UPDATE		
	
	/*	
	 * Defining the input-output relationships (represented by selectivity) of the application modules. 
	 */
	application.addTupleMapping(modulesName.get(i), "SENSED_DATA", "ACTION", new FractionalSelectivity(1.0)); 
	
	final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("SENSED_DATA");add(mName);add("ACTION");}});
	System.out.println("LOOP ID at creation = "+loop1.getLoopId());
	loops.add(loop1);
	}
	
	application.setLoops(loops);
	
	return application;
}


}
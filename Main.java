import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //adjacency list
        HashMap<POI, ArrayList<Pair<POI, Double>>> graph = new HashMap<>();

        //store POIs within hashsets of the same type of POI
        HashSet<POI> picnicSites = new HashSet<>();
        HashSet<POI> playgrounds = new HashSet<>();
        HashSet<POI> pools = new HashSet<>();
        HashSet<POI> playstreets = new HashSet<>();

        //map zipcodes to sets of POIs
        HashMap<Integer, HashSet<POI>> zipcodes = new HashMap<>();
        try {
            //read in picnic sites
            BufferedReader br = new BufferedReader(new FileReader("src/ppr_picnic_sites.csv"));
            String line = br.readLine();
            boolean firstLine = true;
            while (line != null) {
                String[] info = line.split(",");
                if (firstLine) {
                    firstLine = false;
                } else {
                    picnicSites.add(new POI(info[1], Double.parseDouble(info[info.length - 1]), Double.parseDouble(
                            info[info.length - 2]), "picnic", null, 0, Integer.parseInt(info[0])));
                }
                line = br.readLine();
            }
            //read in playgrounds
            br = new BufferedReader(new FileReader("src/PPR_Playgrounds.csv"));
            line = br.readLine();
            firstLine = true;
            while (line != null) {
                String[] info = line.split(",");
                if (firstLine) {
                    firstLine = false;
                } else {
                    playgrounds.add(new POI(info[3], Double.parseDouble(info[0]), Double.parseDouble(info[1]),
                            "playground", null, 0, Integer.parseInt(info[2])));
                }
                line = br.readLine();
            }
            //read in swimming pools
            br = new BufferedReader(new FileReader("src/PPR_Swimming_Pools.csv"));
            line = br.readLine();
            firstLine = true;
            while (line != null) {
                String[] info = line.split(",");
                if (firstLine) {
                    firstLine = false;
                } else {
                    Integer zipcode = Integer.parseInt(info[6]);
                    POI poi = new POI(info[3], Double.parseDouble(info[0]), Double.parseDouble(info[1]),
                            "pool", info[5], zipcode, Integer.parseInt(info[2]));
                    pools.add(poi);
                    if (zipcodes.containsKey(zipcode)) {
                        zipcodes.get(zipcode).add(poi);
                    } else {
                        HashSet<POI> set = new HashSet<>();
                        set.add(poi);
                        zipcodes.put(zipcode, set);
                    }
                }
                line = br.readLine();
            }
            //read in playstreets
            br = new BufferedReader(new FileReader("src/PPR_Playstreets_Locations.csv"));
            line = br.readLine();
            firstLine = true;
            while (line != null) {
                String[] info = line.split(",");
                if (firstLine) {
                    firstLine = false;
                } else {
                    Integer zipcode = Integer.parseInt(info[4]);
                    POI poi = new POI(info[3], Double.parseDouble(info[0]), Double.parseDouble(info[1]),
                            "playstreet", null, Integer.parseInt(info[4]), Integer.parseInt(info[2]));
                    playstreets.add(poi);
                    pools.add(poi);
                    if (zipcodes.containsKey(zipcode)) {
                        zipcodes.get(zipcode).add(poi);
                    } else {
                        HashSet<POI> set = new HashSet<>();
                        set.add(poi);
                        zipcodes.put(zipcode, set);
                    }
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        // iterate through each type of POI, adding edges to the adjacency list
        // if distance is walkable enough (about < 2 mi)
        // consider edges between picnic sites and all other kinds of POI
        for(POI p : picnicSites){
            ArrayList<Pair<POI, Double>> al = new ArrayList<>();
            for(POI q : playgrounds){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : pools){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : playstreets){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            graph.put(p, al);
        }
        // consider edges between playgrounds and all other kinds of POI
        for(POI p : playgrounds){
            ArrayList<Pair<POI, Double>> al = new ArrayList<>();
            for(POI q : picnicSites){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : pools){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : playstreets){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            graph.put(p, al);
        }
        // consider edges between pools and all other kinds of POI
        for(POI p : pools){
            ArrayList<Pair<POI, Double>> al = new ArrayList<>();
            for(POI q : picnicSites){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : playgrounds){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : playstreets){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            graph.put(p, al);
        }
        //add all playstreet zip codes to a set to know which zip codes are available as starting
        //zip codes
        TreeSet<Integer> playstreetZips = new TreeSet<>();
        // consider edges between playstreets and all other kinds of POI
        for(POI p : playstreets){
            playstreetZips.add(p.getZip());
            ArrayList<Pair<POI, Double>> al = new ArrayList<>();
            for(POI q : picnicSites){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : pools){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            for(POI q : playgrounds){
                double dist = p.dist(q);
                if(dist <= 2){
                    Pair<POI, Double> qpair = new Pair<>(q, dist);
                    al.add(qpair);
                }
            }
            graph.put(p, al);
        }
//        for(POI p : playgrounds){
//            String pgName = p.getName();
//            ArrayList<Pair<POI, Double>> arr = graph.get(p);
//            for(Pair<POI, Double> pr : arr){
//                System.out.println("for playground " + pgName + ", " + pr.getKey().getName() + "is "
//                        + pr.getValue() + "miles away");
//            }
//        }
        //prompt for user input
        Scanner sc = new Scanner(System.in);
        System.out.println("Hello!! Welcome to our application :) Given your zip code, we will generate \n" +
                "a potential family-friendly itinerary, perfect for a summer day! \n" +
                "Each stop will feature free, fun, and fitness-oriented enrichment opportunities \n" +
                "for your children, and stops will not be spaced more than 2 miles apart, making for \n" +
                "a walkable or bikeable day! \n");
        System.out.println("Let's get started. Would you like a picnic site in your itinerary? [0 = NO, 1 = YES]");
        String input1 = sc.nextLine();
        while(!(input1.equals("0")) && !(input1.equals("1"))){
            System.out.println("Sorry, we couldn't understand that input. " +
                    "Would you like a picnic site in your itinerary? [0 = NO, 1 = YES]");
            input1 = sc.nextLine();
        }
        System.out.println("How about playgrounds? Would you like a playground in your itinerary? [0 = NO, 1 = YES]");
        String input2 = sc.nextLine();
        while(!(input2.equals("0")) && !(input2.equals("1"))){
            System.out.println("Sorry, we couldn't understand that input. " +
                    "Would you like a playground in your itinerary? [0 = NO, 1 = YES]");
            input2 = sc.nextLine();
        }
        System.out.println("Add a third P! Would you like a pool in your itinerary? [0 = NO, 1 = YES]");
        String input3 = sc.nextLine();
        while(!(input3.equals("0")) && !(input3.equals("1"))){
            System.out.println("Sorry, we couldn't understand that input. " +
                    "Would you like a pool in your itinerary? [0 = NO, 1 = YES]");
            input3 = sc.nextLine();
        }
        System.out.println("Finally, we'll be starting you on a playstreet \n " +
                "(the Philadelphia Parks and Recreation Playstreets Program closes designated streets to traffic when \n " +
                "school is out for kids to play, and particularly, in the summer, these streets provide nutritious meals \n" +
                " and snacks when school meals aren't an option). Let us know which zip code you'd like to start in! \n" +
                "We can service the following Philadelphia zip codes:");
        System.out.println(playstreetZips);
        //make sure zip code is valid
        String input4 = sc.nextLine();
        int input4Int = Integer.parseInt(input4);
        boolean invalid = true;
        for(int z : playstreetZips){
            if(input4Int == z){
                invalid = false;
                break;
            }
        }
        while(invalid || input4.length() != 5){
            System.out.println("Sorry, that's not a valid zip code. " +
                    "Please input a 5-digit zip code from the following serviceable Philadelphia codes:");
            System.out.println(playstreetZips);
            input4 = sc.nextLine();
            input4Int = Integer.parseInt(input4);
            invalid = true;
            for(int z : playstreetZips){
                if(input4Int == z){
                    invalid = false;
                    break;
                }
            }
        }
        System.out.println("Last question! Would you like us to let you know if any of the 46 bookstores in the city \n " +
                "are within your starting zip code? [0 = NO, 1 = YES]");
        String input5 = sc.nextLine();
        while(!(input5.equals("0")) && !(input5.equals("1"))){
            System.out.println("Sorry, we couldn't understand that input. " +
                    "Would you like us to inform you of bookstores within your starting zip code? [0 = NO, 1 = YES]");
            input5 = sc.nextLine();
        }
        //parse input to determine arguments for search() method
        boolean picBool = false;
        boolean playBool = false;
        boolean poolBool = false;
        boolean bstoreBool = false;
        if(input1.equals("1")){
            picBool = true;
        }
        if(input2.equals("1")){
            playBool = true;
        }
        if(input3.equals("1")){
            poolBool = true;
        }
        if(input5.equals("1")){
            bstoreBool = true;
        }
        //run search
        List<POI> results = search(graph, picnicSites, playgrounds, pools, playstreets, zipcodes, true, picBool, playBool, poolBool, input4Int);
        //output
        System.out.println("Amazing! We found the following itinerary for you for starting zip code " + input4Int);
        System.out.println("Start at " + results.get(0));
        if(results.size() > 1) {
            for (int i = 1; i < results.size() - 1; i++) {
                int truncatedDist = (int) (results.get(i - 1).dist(results.get(i)) * 100000);
                double finalDist = truncatedDist / 100000.0;
                System.out.println("Next stop (" + finalDist + " miles away from previous stop): " + results.get(i));
            }
            int truncatedDist = (int) (results.get(results.size() - 2).dist(results.get(results.size() - 1)) * 100);
            double finalDist = truncatedDist / 100000.0;
            System.out.println("End (" + finalDist + " miles away from previous stop) at " + results.get(results.size() - 1));
        }
        //if user requested for bookstores
        if(bstoreBool){
            System.out.println("Hold on while we look for bookstores within your zip code... :)");
            //scrape bookstore site
            ArrayList<POI> validStores = new ArrayList<>();
            BookstoreParser bp = new BookstoreParser();
            bp.getStores();
            ArrayList<POI> stores = bp.stores;
            for(POI s : stores){
                //add bookstores within the desired zip code to the running collection
                if(s.getZip() == input4Int){
                    validStores.add(s);
                }
            }
            //print out bookstores in the desired zip code, if there are any
            if(!validStores.isEmpty()) {
                System.out.println("Within zip code " + input4Int + ", we found the following bookstore(s): ");
                for(POI s : validStores){
                    System.out.println(s);
                }
            }
            else{
                System.out.println("Unfortunately, there are no bookstores within the zip code " + input4Int + ".");
            }
        }
        System.out.println("Please note that we tried our best to fulfill each type of point of interest that you requested. However, for example, if your starting point was too far from pools, we may not have any pools in your recommended itinerary.");
    }

    /* given the graph and hash sets of types of POI, as well as the hashmap of zip codes to POI, as well as
    user's input (preferences and zip code), generate a shortest path starting at a playstreet*/
    public static List<POI> search(HashMap<POI, ArrayList<Pair<POI, Double>>> graph, HashSet<POI> picnicSites,
                                   HashSet<POI> playgrounds, HashSet<POI> pools, HashSet<POI> playstreets, HashMap<Integer, HashSet<POI>> zipcodes,
                                   boolean playstreet, boolean picnic, boolean playground, boolean pool, int code) {
        //keep track of types of POI visited
        ArrayList<Boolean> found = new ArrayList<>();
        found.add(false);
        found.add(false);
        found.add(false);
        found.add(false);
        ArrayList<Boolean> ideal = new ArrayList<>();
        ideal.add(true);
        ideal.add(true);
        ideal.add(true);
        ideal.add(true);

        //for each type of POI, make sure to set them to not visited
        HashMap<Integer, Boolean> picnicExpl = new HashMap<>();
        for (POI site : picnicSites) {
            picnicExpl.put(site.getId(), false);
        }
        HashMap<Integer, Boolean> psExpl = new HashMap<>();
        for (POI street : playstreets) {
            psExpl.put(street.getId(), false);
        }
        HashMap<Integer, Boolean> pgExpl = new HashMap<>();
        for (POI pg : playgrounds) {
            pgExpl.put(pg.getId(), false);
        }
        HashMap<Integer, Boolean> poolExpl = new HashMap<>();
        for (POI sp : pools) {
            poolExpl.put(sp.getId(), false);
        }
        if (!playstreet) {
            found.set(0, true);
        }
        if (!picnic) {
            found.set(1, true);
        }
        if (!playground) {
            found.set(2, true);
        }
        if (!pool) {
            found.set(3, true);
        }
        //find a starting playstreet
        POI starter = null;
        for (POI possible : zipcodes.get(code)) {
            if (possible.getType().equals("playstreet")) {
                starter = possible;
                break;
            }
        }
        //use priority queue to keep track of nodes, keep track of which types of POI have been visited
        PriorityQueue<Quad<POI, Double, POI, ArrayList<Boolean>>> minHeap = new PriorityQueue<>();
        HashMap<POI, POI> parents = new HashMap<>();
        Quad<POI, Double, POI, ArrayList<Boolean>> cur = new Quad<>(starter, (double) 0, null, found);
        minHeap.add(cur);
        while (!minHeap.isEmpty()) {
            cur = minHeap.poll();
            found = cur.getBoolArr();
            if (cur.getKey().getType().equals("playstreet")) {
                psExpl.put(cur.getKey().getId(), true);
                found.set(0, true);
            }
            if (cur.getKey().getType().equals("picnic")) {
                picnicExpl.put(cur.getKey().getId(), true);
                found.set(1, true);
            }
            if (cur.getKey().getType().equals("pool")) {
                poolExpl.put(cur.getKey().getId(), true);
                found.set(3, true);
            }
            if (cur.getKey().getType().equals("playground")) {
                pgExpl.put(cur.getKey().getId(), true);
                found.set(2, true);
            }
            parents.put(cur.getKey(), cur.getParent());
            if (found.equals(ideal)) {
                LinkedList<POI> path = new LinkedList<>();
                POI trace = cur.getKey();
                while (!trace.equals(starter)) {
                    path.addFirst(trace);
                    trace = parents.get(trace);
                }
                path.addFirst(starter);
                return path;
            }

            for (Pair<POI, Double> neighbor : graph.get(cur.getKey())) {
                POI npoi = neighbor.getKey();
                if ((npoi.getType().equals("playstreet") && !psExpl.get(npoi.getId()) && !found.get(0))
                        || (npoi.getType().equals("picnic") && !picnicExpl.get(npoi.getId()) && !found.get(1))
                        || (npoi.getType().equals("playground") && !pgExpl.get(npoi.getId()) && !found.get(2))
                        || (npoi.getType().equals("pool") && !poolExpl.get(npoi.getId()) && !found.get(3))) {
                    Quad<POI, Double, POI, ArrayList<Boolean>> temp = new Quad<>(npoi, cur.getValue()
                            + neighbor.getValue(), cur.getKey(), new ArrayList<>(cur.getBoolArr()));
                    minHeap.add(temp);
                }
            }
        }
        return new ArrayList<>();
    }
}
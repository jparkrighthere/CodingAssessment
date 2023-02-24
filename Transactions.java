import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class transactions {

  public static Map<String, Integer> pairs = new HashMap<>();
  public static List<TransactionsInfo> transactionsInfos = new ArrayList<>();

  public static class TransactionsInfo{

    private String payer;
    private int points;
    private Date timestamp;

    public TransactionsInfo(String payer, int points, Date timestamp) {
      this.payer = payer;
      this.points = points;
      this.timestamp = timestamp;
    }

    public String getPayer() {
      return this.payer;
    }

    public int getPoints() {
      return this.points;   
    }

    public Date getTimestamp() {
      return this.timestamp;
    }

    public void pointsSpend (int p) {
      this.points -= p;
    }
  }

  public static void readFile(String filename) throws ParseException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))){
      String info;
      boolean first=true;
      while ((info = reader.readLine()) != null) {
        if (first) {
          first=false;
          continue;
        }
        String[] data = info.split(",");
        String payer = data[0].trim();
        int points = Integer.parseInt(data[1].trim());
        Date timestamp = dateFormat.parse(data[2].trim()); 
        TransactionsInfo transactionsInfo = new TransactionsInfo(payer,points,timestamp);
        transactionsInfos.add(transactionsInfo);
        pairs.putIfAbsent(payer, 0);
        pairs.compute(payer, (k, v) -> v + points);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void pointsSpend(int points) {
    transactionsInfos.sort(Comparator.comparing(TransactionsInfo::getTimestamp));
    
    for (TransactionsInfo transactionsinfo : transactionsInfos) {
      int pointsSpent = Math.min(points, transactionsinfo.getPoints());
      if (pointsSpent > 0) {
        points = points - pointsSpent;
        transactionsinfo.pointsSpend(pointsSpent);
        pairs.compute(transactionsinfo.getPayer(), (k, v) -> v - pointsSpent);
      }
      if (points==0) {
        break;
      }
    }
  }

  public static void main(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java transactions <points> <filename.csv>");
      System.exit(1);
    }
    int points = Integer.parseInt(args[0]);
    String filename = args[1];
    try {
      readFile(filename);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    pointsSpend(points);
    System.out.println(pairs);
  }

}

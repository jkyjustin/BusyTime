package MainApp;

/**
 * Created by jkyju_000 on 5/6/2016.
 */
public class Entry implements Comparable<Entry>{
    private int datey, datem, dated, timeh, timem, serviceTime;

    public Entry(int datey, int datem, int dated, int timeh, int timem, int serviceTime) {
        this.datey = datey;
        this.datem = datem;
        this.dated = dated;
        this.timeh = timeh;
        this.timem = timem;
        this.serviceTime = serviceTime;
    }

    public int getDatey() {
        return datey;
    }

    public int getDatem() {
        return datem;
    }

    public int getDated() {
        return dated;
    }

    public int getTimeh() {
        return timeh;
    }

    public int getTimem() {
        return timem;
    }

    public int getServiceTime() {
        return serviceTime;
    }


    // sortable by:
    // datey descending
    // datem descending,
    // dated descending,
    // timeh ascending,
    // timem ascending
    // no sorting on servicetime, irrelevant to main function whether it is sorted or not.
    @Override
    public int compareTo(Entry other) {
        int i = other.datey - this.datey;
        if (i != 0) return i;

        i = other.datem - this.datem;
        if (i != 0) return i;

        i = other.dated - this.dated;
        if (i != 0) return i;

        i = this.timeh - other.timeh;
        if (i != 0) return i;

        return this.timem - other.timem;
    }
}

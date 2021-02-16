package Model;
//Model to get and set daily weight of given user
public class DailyWeight {
    private float mDaily;
    private  int mDay;
    private  int mId;

    public DailyWeight() {}

    public DailyWeight(int id, int day, float daily) {
        this.mId = id;
        this.mDaily = daily;
        this.mDay = day;
    }

    public DailyWeight(float daily) {
        this.mDaily = daily;
    }

    public DailyWeight(int day, float daily) {
        this.mDaily = daily;
        this.mDay = day;
    }

    @Override
    public String toString() {
        return "DailyWeight{" +
                "mDaily=" + mDaily +
                ", mDay=" + mDay +
                ", mId=" + mId +
                '}';
    }

    public void DailyWeight(int id, int day, float daily) {
        this.mId = id;
        this.mDaily = daily;
        this.mDay = day;
    }


    public float getDaily() {
        return mDaily;
    }

    public void setDaily(float mDaily) {
        this.mDaily = mDaily;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int mDay) {
        this.mDay = mDay;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

}

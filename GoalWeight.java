package Model;
//Model to get and set goal weight of given user
public class GoalWeight {
    private Float mGoal;
    private  int mId;
    private  int mRowCount;

    public GoalWeight() {}

    @Override
    public String toString() {
        return "GoalWeight{" +
                "mGoal=" + mGoal +
                ", mId=" + mId +
                ", mRowCount=" + mRowCount +
                '}';
    }

    public void GoalWeight(int id, Float goal) {
        this.mId = id;
        this.mGoal = goal;
    }
    public void RowCount(int id, int rowcount) {
        this.mId = id;
        this.mRowCount = rowcount;
    }

    public Float getGoal() {
        return mGoal;
    }

    public void setGoal(Float mGoal) {
        this.mGoal = mGoal;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
    public int getRowCount() {return  mRowCount; }
    public  void setRowCount(int mRowCount){this.mRowCount = mRowCount; }
}

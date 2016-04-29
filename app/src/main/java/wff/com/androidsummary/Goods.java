package wff.com.androidsummary;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wufeifei on 2016/4/19.
 */
public class Goods implements Parcelable {
    private String name;
    private int count;

    public Goods() {
    }

    public Goods(String name, int count) {
        this.name = name;
        this.count = count;
    }

    protected Goods(Parcel in) {
        name = in.readString();
        count = in.readInt();
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        count++;
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(count);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}

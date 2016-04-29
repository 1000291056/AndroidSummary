// IMyAidlInterface.aidl
package wff.com.androidsummary;
import wff.com.androidsummary.Goods;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
     void setGoods(in Goods goods);
     int getCount();
}

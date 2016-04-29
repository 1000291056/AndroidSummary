package wff.com.androidsummary;

/**
 * Created by wufeifei on 2016/4/21.
 */
public interface DbDao {
    /**
     * 增加
     *
     * @param name
     * @param age
     * @return
     */
    public long insert(String name, int age);

    /**
     * 删
     *
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * 改
     *
     * @param student
     * @return
     */
    public int update(Student student);

    /**
     * 查
     *
     * @param id
     * @return
     */
    public Student query(int id);
}

import java.util.List;

public class MethodInfo {
    String name;
    boolean isAbstract;
    List<Class> args;
    Class returnType;

    public MethodInfo(String find, boolean b, List<Class> classes, Class objectClass) {
        this.name = find;
        this.isAbstract = b;
        this.args = classes;
        this.returnType = objectClass;
    }
}

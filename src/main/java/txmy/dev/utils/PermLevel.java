package txmy.dev.utils;

public enum PermLevel {

    USER("hungergames.user"), VIP("hungergames.vip"), ADMIN("hungergames.admin");

    String permission;

    PermLevel(String permission) {
        this.permission = permission;
    }

    String getPermission(){
        return permission;
    }
}

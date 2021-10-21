package ch.supsi.webapp.web.model;

public class Success {
    private boolean success;

    public Success(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

package ch.erp.management.mvp.model.http.load;


public interface ILoadCallback {

    public void onNetException();

    public void onItemClick(int position, Object itemObject);
}

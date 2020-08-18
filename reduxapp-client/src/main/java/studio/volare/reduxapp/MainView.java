package studio.volare.reduxapp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


public class MainView extends Composite {
    interface MainViewUiBinder extends UiBinder<Widget, MainView> {
    }

    private static MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);

    @UiField
    Label title;



    public MainView(ViewModel vm) {
        initWidget(ourUiBinder.createAndBindUi(this));
        title.setText(vm.getTitle());
    }

    //Come la mia view vede il mondo (store e state)
    public abstract static class ViewModel{
        public abstract String getTitle();
        public abstract void setLoading(boolean loading);
    }


}
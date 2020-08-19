package studio.volare.reduxapp;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialCard;
import gwt.material.design.client.ui.MaterialCardContent;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLoader;


public class MainView extends Composite {
    interface MainViewUiBinder extends UiBinder<Widget, MainView> {
    }

    private static MainViewUiBinder ourUiBinder = GWT.create(MainViewUiBinder.class);

    ViewModel vm;

    @UiField
    MaterialLabel label;

    @UiField
    MaterialCardContent cardContent;

    @UiHandler("button")
    public void onClick(ClickEvent clickEvent){
        vm.changeTitle(getRandom());
    }

    public MainView(ViewModel vm) {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.vm = vm;
        label.setText(vm.getText());
        if(vm.isLoading()){
            MaterialLoader.loading(true, cardContent);
        }else {
            MaterialLoader.loading(false, cardContent);
        }

    }

    //Come la mia view vede il mondo (store e state)
    public abstract static class ViewModel{
        public abstract boolean isLoading();
        public abstract String getText();
        public abstract void changeTitle(String title);
    }

    String getRandom(){
        StringBuilder randomCodes = new StringBuilder(String.valueOf((int) (Math.random() * (99999 - 1) + 1)));
        while (randomCodes.length() < 5) {
            randomCodes.insert(0, "0");
        }
        return randomCodes.toString();
    }


}
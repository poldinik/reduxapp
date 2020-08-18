package studio.volare.reduxapp.redux;

import com.google.gwt.user.client.ui.Widget;

public interface ViewModelBuilder<ViewModel> {
    Widget build(ViewModel vm);
}

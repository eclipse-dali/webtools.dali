package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.jpa2.context.DerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.OneToOneMapping2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractOneToOneMappingComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractOneToOneMapping2_0Composite<T extends OneToOneMapping2_0>
	extends AbstractOneToOneMappingComposite<T>
{
	protected AbstractOneToOneMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
	
	
	protected PropertyValueModel<DerivedId2_0> buildDerivedIdHolder() {
		return new PropertyAspectAdapter<T, DerivedId2_0>(getSubjectHolder()) {
			@Override
			protected DerivedId2_0 buildValue_() {
				return this.subject.getDerivedId();
			}
		};
	}	
}

package org.eclipse.jpt.ui.internal.jpa2.details;

import org.eclipse.jpt.core.jpa2.context.DerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.ManyToOneMapping2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AbstractManyToOneMappingComposite;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractManyToOneMapping2_0Composite<T extends ManyToOneMapping2_0>
	extends AbstractManyToOneMappingComposite<T>
{
	protected AbstractManyToOneMapping2_0Composite(
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

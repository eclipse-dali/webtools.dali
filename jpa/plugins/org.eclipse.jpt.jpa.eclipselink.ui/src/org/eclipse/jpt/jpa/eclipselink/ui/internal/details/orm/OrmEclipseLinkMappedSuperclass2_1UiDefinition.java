package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappedSuperclassUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkMappedSuperclass2_1UiDefinition extends
		AbstractMappedSuperclassUiDefinition<PersistentType, OrmEclipseLinkMappedSuperclass> implements
		OrmTypeMappingUiDefinition<OrmEclipseLinkMappedSuperclass> {

	// singleton
	private static final OrmEclipseLinkMappedSuperclass2_1UiDefinition INSTANCE = 
			new OrmEclipseLinkMappedSuperclass2_1UiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingUiDefinition<OrmEclipseLinkMappedSuperclass> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkMappedSuperclass2_1UiDefinition() {
		super();
	}
	
	
	public JpaComposite buildTypeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmEclipseLinkMappedSuperclass> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return ((EclipseLinkOrmXml2_1UiFactory)factory).
				createOrmEclipseLinkMappedSuperclass2_1Composite(subjectHolder, parent, widgetFactory);
	}
}

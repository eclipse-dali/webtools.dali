/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.mappings.details.OrmPersistentAttributeMapAsComposite;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmPersistentAttributeMapAsComposite                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Attribute mapping pane                                                | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmPersistentAttribute
 * @see OrmPersistentAttributeMapAsComposite
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class OrmPersistentAttributeDetailsPage extends PersistentAttributeDetailsPage<OrmPersistentAttribute>
{
	/**
	 * Creates a new <code>OrmPersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmPersistentAttributeDetailsPage(Composite parent,
	                                         WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		return jpaPlatformUi().ormAttributeMappingUiProviders();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected AttributeMappingUiProvider<? extends AttributeMapping>[] attributeMappingUiProvidersFor(PersistentAttribute persistentAttribute) {
		//bug 192035 - no default mapping option in xml
		return CollectionTools.array(attributeMappingUiProviders(), new AttributeMappingUiProvider[CollectionTools.size(attributeMappingUiProviders())]);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected AttributeMappingUiProvider<AttributeMapping> defaultAttributeMappingUiProvider(String key) {
		throw new UnsupportedOperationException("Xml attributeMappings should not be default");
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		return jpaPlatformUi().defaultOrmAttributeMappingUiProviders();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		updateEnbabledState();
	}

//	private PropertyValueModel<OrmAttributeMapping> getMappingHolder() {
//		return new TransformationPropertyValueModel<PersistentAttribute, OrmAttributeMapping>(getSubjectHolder()) {
//			@Override
//			protected OrmAttributeMapping transform_(PersistentAttribute value) {
//				return (OrmAttributeMapping) value.getMapping();
//			}
//		};
//	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Map As composite
		new OrmPersistentAttributeMapAsComposite(
			this,
			buildSubPane(container, 0, 0, 5, 0)
		);

		// Mapping properties pane
		PageBook attributePane = buildMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		attributePane.setLayoutData(gridData);
	}

	public void updateEnabledState(boolean enabled, Control control) {
		control.setEnabled(enabled);
		if (control instanceof Composite) {
			for (Iterator<Control> i = new ArrayIterator<Control>(((Composite) control).getChildren()); i.hasNext(); ) {
				updateEnabledState(enabled, i.next());
			}
		}
	}

	public void updateEnbabledState() {
		if (subject() == null || subject().parent() == null) {
			return;
		}
		boolean enabled = !subject().isVirtual();
		updateEnabledState(enabled, getControl());
	}
}
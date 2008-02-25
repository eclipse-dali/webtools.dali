/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.java.details.BasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.IdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.details.VersionMappingUiProvider;
import org.eclipse.jpt.ui.java.details.AttributeMappingUiProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 *
 * @see PersistentAttribute
 * @see OrmJavaAttributeChooser
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class OrmPersistentAttributeDetailsPage extends PersistentAttributeDetailsPage<OrmPersistentAttribute>
{
	private List<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders;

	/**
	 * Creates a new <code>XmlPersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmPersistentAttributeDetailsPage(Composite parent,
	                                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		providers.add(BasicMappingUiProvider.instance());
		providers.add(EmbeddedMappingUiProvider.instance());
		providers.add(EmbeddedIdMappingUiProvider.instance());
		providers.add(IdMappingUiProvider.instance());
		providers.add(ManyToManyMappingUiProvider.instance());
		providers.add(ManyToOneMappingUiProvider.instance());
		providers.add(OneToManyMappingUiProvider.instance());
		providers.add(OneToOneMappingUiProvider.instance());
		providers.add(TransientMappingUiProvider.instance());
		providers.add(VersionMappingUiProvider.instance());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		if (this.attributeMappingUiProviders == null) {
			this.attributeMappingUiProviders = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
			this.addAttributeMappingUiProvidersTo(this.attributeMappingUiProviders);
		}

		return new CloneListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(
			this.attributeMappingUiProviders
		);
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
	protected ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		return EmptyListIterator.instance();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		updateEnbabledState();
	}

	private PropertyValueModel<AbstractOrmAttributeMapping<? extends XmlAttributeMapping>> getMappingHolder() {
		return new TransformationPropertyValueModel<PersistentAttribute, AbstractOrmAttributeMapping<? extends XmlAttributeMapping>>(getSubjectHolder()) {
			@Override
			@SuppressWarnings("unchecked")
			protected AbstractOrmAttributeMapping<? extends XmlAttributeMapping> transform_(PersistentAttribute value) {
				return (AbstractOrmAttributeMapping<? extends XmlAttributeMapping>) value.getMapping();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Entity type widgets
		new OrmJavaAttributeChooser(this, getMappingHolder(), container);

		// Note: The combo's parent is a container fixing the issue with the
		// border not being painted
		buildLabeledComposite(
			container,
			buildMappingLabel(container),
			buildMappingCombo(container).getControl().getParent()
		);

		// Properties pane
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
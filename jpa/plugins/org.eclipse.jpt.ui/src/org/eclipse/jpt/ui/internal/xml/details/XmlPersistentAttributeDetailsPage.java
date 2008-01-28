/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.ui.internal.details.PersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.BasicMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedIdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddedMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.IdMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.ManyToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToManyMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.OneToOneMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.TransientMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.VersionMappingUiProvider;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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
 * @see IPersistentAttribute
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class XmlPersistentAttributeDetailsPage extends PersistentAttributeDetailsPage
{
	private XmlJavaAttributeChooser javaAttributeChooser;
	private List<IAttributeMappingUiProvider<? extends IAttributeMapping>> attributeMappingUiProviders;

	/**
	 * Creates a new <code>XmlPersistentAttributeDetailsPage</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public XmlPersistentAttributeDetailsPage(PropertyValueModel<? extends IPersistentAttribute> subjectHolder,
	                                         Composite parent,
	                                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>> attributeMappingUiProviders() {
		if (this.attributeMappingUiProviders == null) {
			this.attributeMappingUiProviders = new ArrayList<IAttributeMappingUiProvider<? extends IAttributeMapping>>();
			this.addAttributeMappingUiProvidersTo(this.attributeMappingUiProviders);
		}

		return new CloneListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>>(
			this.attributeMappingUiProviders
		);
	}

	protected void addAttributeMappingUiProvidersTo(List<IAttributeMappingUiProvider<? extends IAttributeMapping>> providers) {
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
	protected ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>> defaultAttributeMappingUiProviders() {
		return EmptyListIterator.instance();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected IAttributeMappingUiProvider<IAttributeMapping> defaultAttributeMappingUiProvider(String key) {
		throw new UnsupportedOperationException("Xml attributeMappings should not be default");
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected IAttributeMappingUiProvider<? extends IAttributeMapping>[] attributeMappingUiProvidersFor(IPersistentAttribute persistentAttribute) {
		//bug 192035 - no default mapping option in xml
		return CollectionTools.array(attributeMappingUiProviders(), new IAttributeMappingUiProvider[CollectionTools.size(attributeMappingUiProviders())]);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		buildLabeledComposite(
			container,
			JptUiXmlMessages.PersistentAttributePage_javaAttributeLabel,
			buildMappingCombo(container).getControl()
		);

		PageBook attributePane = buildMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		attributePane.setLayoutData(gridData);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.javaAttributeChooser.populate();
		updateEnbabledState();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {
		this.javaAttributeChooser.dispose();
		super.dispose();
	}

	public void updateEnbabledState() {
		if (subject() == null || subject().parent() == null) {
			return;
		}
		boolean enabled = !((XmlPersistentAttribute) subject()).isVirtual();
		updateEnabledState(enabled, getControl());
	}

	public void updateEnabledState(boolean enabled, Control control) {
		control.setEnabled(enabled);
		if (control instanceof Composite) {
			for (Iterator<Control> i = new ArrayIterator<Control>(((Composite) control).getChildren()); i.hasNext(); ) {
				updateEnabledState(enabled, i.next());
			}
		}
	}
}
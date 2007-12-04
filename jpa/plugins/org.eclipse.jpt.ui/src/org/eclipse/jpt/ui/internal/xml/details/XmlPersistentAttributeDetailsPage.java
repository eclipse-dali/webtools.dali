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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IPersistentAttribute;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.content.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.content.orm.XmlPersistentType;
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
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlPersistentAttributeDetailsPage
	extends PersistentAttributeDetailsPage 
{
	private XmlJavaAttributeChooser javaAttributeChooser;
	
	private Adapter persistentTypeListener;
	
	private IPersistentType persistentType;
	
	private List<IAttributeMappingUiProvider> attributeMappingUiProviders;

	public XmlPersistentAttributeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
		buildPersistentTypeListener();
	}
	
	private void buildPersistentTypeListener() {
		this.persistentTypeListener = new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistentTypeChanged(notification);
			}
		};
	}
	
	void persistentTypeChanged(Notification notification) {
		if (notification.getFeatureID(XmlPersistentType.class) == 
			OrmPackage.XML_PERSISTENT_TYPE__SPECIFIED_ATTRIBUTE_MAPPINGS) {
			Display.getDefault().asyncExec(
				new Runnable() {
					public void run() {
						updateEnbabledState();
					}
				});
		}
	}

	@Override
	public ListIterator<IAttributeMappingUiProvider> attributeMappingUiProviders() {
		if (this.attributeMappingUiProviders == null) {
			this.attributeMappingUiProviders = new ArrayList<IAttributeMappingUiProvider>();
			this.addAttributeMappingUiProvidersTo(this.attributeMappingUiProviders);
		}
		return new CloneListIterator<IAttributeMappingUiProvider>(this.attributeMappingUiProviders);

	}

	protected void addAttributeMappingUiProvidersTo(List<IAttributeMappingUiProvider> providers) {
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
	
	@Override
	protected ListIterator<IAttributeMappingUiProvider> defaultAttributeMappingUiProviders() {
		return EmptyListIterator.instance();
	}
	
	@Override
	protected IAttributeMappingUiProvider defaultAttributeMappingUiProvider(String key) {
		throw new UnsupportedOperationException("Xml attributeMappings should not be default");
	}
	
	@Override
	//bug 192035 - no default mapping option in xml
	protected IAttributeMappingUiProvider[] attributeMappingUiProvidersFor(IPersistentAttribute persistentAttribute) {
		return CollectionTools.array(attributeMappingUiProviders(), new IAttributeMappingUiProvider[CollectionTools.size(attributeMappingUiProviders())]);
	}

	@Override
	protected void initializeLayout(Composite composite) {
		composite.setLayout(new GridLayout(2, false));
		
		GridData gridData;
		
		CommonWidgets.buildJavaAttributeNameLabel(composite, getWidgetFactory());
		
		this.javaAttributeChooser = CommonWidgets.buildJavaAttributeChooser(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.javaAttributeChooser.getControl().setLayoutData(gridData);

		
		buildMappingLabel(composite);
		
		ComboViewer mappingCombo = buildMappingCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		mappingCombo.getCombo().setLayoutData(gridData);
		
		PageBook book = buildMappingPageBook(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		book.setLayoutData(gridData);
	}
	
	@Override
	protected void engageListeners() {
		super.engageListeners();
		if (getAttribute() != null) {
			this.persistentType = getAttribute().typeMapping().getPersistentType();
			this.persistentType.eAdapters().add(this.persistentTypeListener);
		}
	}
	
	@Override
	protected void disengageListeners() {
		if (this.persistentType != null) {
			this.persistentType.eAdapters().remove(this.persistentTypeListener);
			this.persistentType = null;
		}
		super.disengageListeners();
	}
	
	@Override
	protected void doPopulate(IJpaContentNode persistentAttributeNode) {
		super.doPopulate(persistentAttributeNode);
		if (persistentAttributeNode == null) {
			this.javaAttributeChooser.populate(null);
		}
		else {
			XmlAttributeMapping mapping = ((XmlPersistentAttribute) persistentAttributeNode).getMapping();
			this.javaAttributeChooser.populate(mapping);
			updateEnbabledState();
		}
	}
	
	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.javaAttributeChooser.populate();
		updateEnbabledState();
	}
	
	@Override
	public void dispose() {
		this.javaAttributeChooser.dispose();
		super.dispose();
	}
	
	public void updateEnbabledState() {
		if (getAttribute() == null || getAttribute().eContainer() == null) {
			return;
		}
		boolean enabled = !((XmlPersistentAttribute) getAttribute()).isVirtual();
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
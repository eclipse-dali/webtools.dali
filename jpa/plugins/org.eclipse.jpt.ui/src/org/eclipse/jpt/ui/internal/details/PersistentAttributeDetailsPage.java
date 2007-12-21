/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class PersistentAttributeDetailsPage extends BaseJpaDetailsPage<IPersistentAttribute>
{
	private Adapter persistentAttributeListener;
	private String currentMappingKey;
	private ComboViewer mappingCombo;
	private Map<String, IJpaComposite<? extends IAttributeMapping>> mappingComposites;
	protected PageBook mappingPageBook;
	private IJpaComposite<? extends IAttributeMapping> currentMappingComposite;

	public PersistentAttributeDetailsPage(PropertyValueModel<? extends IPersistentAttribute> subjectHolder,
                                         Composite parent,
                                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, SWT.NONE, widgetFactory);
		this.persistentAttributeListener = buildAttributeListener();
		this.mappingComposites = new HashMap<String, IJpaComposite<? extends IAttributeMapping>>();
	}

	protected IJpaPlatformUi jpaPlatformUi() {
		String platformId = subject().jpaProject().jpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
	}

	protected IJpaUiFactory jpaUiFactory() {
		// TODO: Remove the type cast, used for testing
		return ((BaseJpaPlatformUi) jpaPlatformUi()).getJpaUiFactory();
	}

	protected abstract ListIterator<IAttributeMappingUiProvider> attributeMappingUiProviders();

	protected abstract ListIterator<IAttributeMappingUiProvider> defaultAttributeMappingUiProviders();

	protected IAttributeMappingUiProvider attributeMappingUiProvider(String key) {
		for (ListIterator<IAttributeMappingUiProvider> i = attributeMappingUiProviders(); i.hasNext(); ) {
			IAttributeMappingUiProvider provider = i.next();
			if (provider.attributeMappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported attribute mapping UI provider key: ");
	}

	protected abstract IAttributeMappingUiProvider defaultAttributeMappingUiProvider(String key);

	private Adapter buildAttributeListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				persistentAttributeChanged(notification);
			}
		};
	}

	protected void persistentAttributeChanged(Notification notification) {
		switch (notification.getFeatureID(IPersistentAttribute.class)) {
			case JpaCorePackage.IPERSISTENT_ATTRIBUTE__MAPPING:
				Display.getDefault().asyncExec(
					new Runnable() {
						public void run() {
							populate();
						}
					});
				break;
		}
	}

	protected Label buildMappingLabel(Composite parent) {
		return getWidgetFactory().createLabel(parent, JptUiMessages.PersistentAttributePage_mapAs);
	}

	protected ComboViewer buildMappingCombo(Composite parent) {
		CCombo combo = getWidgetFactory().createCCombo(parent);
		this.mappingCombo = new ComboViewer(combo);
		this.mappingCombo.setContentProvider(buildContentProvider());
		this.mappingCombo.setLabelProvider(buildLabelProvider());
		this.mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				mappingChanged(event);
			}
		});
		return this.mappingCombo;
	}

	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}

			public Object[] getElements(Object inputElement) {
				if (inputElement == null) {
					return new Object[]{};
				}
				return attributeMappingUiProvidersFor((IPersistentAttribute) inputElement);
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}

	protected abstract IAttributeMappingUiProvider[] attributeMappingUiProvidersFor(IPersistentAttribute persistentAttribute);

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IAttributeMappingUiProvider) element).label();
			}
		};
	}

	protected PageBook buildMappingPageBook(Composite parent) {
		this.mappingPageBook = new PageBook(parent, SWT.NONE);
		return this.mappingPageBook;
	}

	private PropertyValueModel<IAttributeMapping> buildMappingHolder() {
		// TODO: Have TransformationPropertyValueModel and
		// TransformationWritablePropertyValueModel
		return new TransformationPropertyValueModel<IPersistentAttribute, IAttributeMapping>((WritablePropertyValueModel<IPersistentAttribute>) getSubjectHolder()) {
			@Override
			protected IAttributeMapping transform(IPersistentAttribute value) {
				return (value == null) ? null : value.getMapping();
			}
		};
	}

	protected IJpaComposite<? extends IAttributeMapping> buildMappingComposite(PageBook pageBook, String key) {
		if (this.subject().getMapping() == null || this.subject().getMapping().isDefault()) {
			return defaultAttributeMappingUiProvider(key).buildAttributeMappingComposite(jpaUiFactory(), buildMappingHolder(), pageBook, getWidgetFactory());
		}

		return attributeMappingUiProvider(key).buildAttributeMappingComposite(jpaUiFactory(), buildMappingHolder(), pageBook, getWidgetFactory());
	}

	void mappingChanged(SelectionChangedEvent event) {
		if (isPopulating()) {
			return;
		}
		if (event.getSelection() instanceof StructuredSelection) {
			IAttributeMappingUiProvider provider = (IAttributeMappingUiProvider) ((StructuredSelection) event.getSelection()).getFirstElement();
			String key = (CollectionTools.contains(defaultAttributeMappingUiProviders(), provider) ? null : provider.attributeMappingKey());
			this.subject().setSpecifiedMappingKey(key);
		}
	}

	@Override
	protected void doPopulate() {
		populateMappingComboAndPage();
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			//TODO this.subject().eAdapters().add(this.persistentAttributeListener);
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			//TODO this.subject().eAdapters().remove(this.persistentAttributeListener);
		}
	}

	private void populateMappingComboAndPage() {
//		this.currentMappingKey = null;
//		this.mappingCombo.setInput(null);
//		this.mappingCombo.setSelection(StructuredSelection.EMPTY);

//		if (this.currentMappingComposite != null) {
//			this.currentMappingComposite.populate();
//			this.currentMappingComposite = null;
//		}

		setComboData();

		IAttributeMapping mapping = this.subject().getMapping();
		populateMappingPage(mapping == null ? null : mapping.getKey());
	}

	private void populateMappingPage(String mappingKey) {
		if (this.currentMappingComposite != null &&
		    this.currentMappingKey == mappingKey) {

			this.currentMappingComposite.populate();
			return;
		}

		// TODO: Remove the type casting
		if (this.currentMappingComposite != null) {
			this.removePaneForAlignment((BaseJpaController<?>) this.currentMappingComposite);
		}

		this.currentMappingKey = mappingKey;
		this.currentMappingComposite = mappingCompositeFor(mappingKey);
		this.mappingPageBook.showPage(this.currentMappingComposite.getControl());

		// TODO: Remove the type casting
		this.addPaneForAlignment((BaseJpaController<?>) this.currentMappingComposite);

		try {
			this.currentMappingComposite.populate();
		}
		finally {
			// Log or show error
		}
	}

	private void setComboData() {
		if (this.subject() != this.mappingCombo.getInput()) {
			this.mappingCombo.setInput(this.subject());
		}
		if (this.subject().getMapping() == null || this.subject().getMapping().isDefault()) {
			this.mappingCombo.setSelection(new StructuredSelection(this.mappingCombo.getElementAt(0)));
		}
		else {
			IAttributeMappingUiProvider provider = attributeMappingUiProvider(this.subject().mappingKey());
			if (provider != null && ! provider.equals(((StructuredSelection) this.mappingCombo.getSelection()).getFirstElement())) {
				this.mappingCombo.setSelection(new StructuredSelection(provider));
			}
		}
	}

	private IJpaComposite<? extends IAttributeMapping> mappingCompositeFor(String key) {
		IJpaComposite<? extends IAttributeMapping> composite = this.mappingComposites.get(key);
		if (composite != null) {
			return composite;
		}

		composite = buildMappingComposite(this.mappingPageBook, key);

		if (composite != null) {
			this.mappingComposites.put(key, composite);
		}

		return composite;
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return mappingCombo.getCombo().setFocus();
//	}

	@Override
	public void dispose() {
		for (IJpaComposite<? extends IAttributeMapping> composite : this.mappingComposites.values()) {
			composite.dispose();
		}
		super.dispose();
	}
}
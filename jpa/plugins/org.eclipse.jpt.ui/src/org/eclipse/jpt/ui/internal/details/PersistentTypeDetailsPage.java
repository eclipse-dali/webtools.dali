/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.java.details.TypeMappingUiProvider;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent type.
 *
 * @see PersistentType
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class PersistentTypeDetailsPage<T extends PersistentType> extends AbstractJpaDetailsPage<T>
{
	private JpaComposite<TypeMapping> currentMappingComposite;
	private String currentMappingKey;
	private Map<String, JpaComposite<TypeMapping>> mappingComposites;
	private ComboViewer typeMappingCombo;
	private PageBook typeMappingPageBook;

	/**
	 * Creates a new <code>PersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistentTypeDetailsPage(Composite parent,
                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentType.MAPPING_PROPERTY);
	}

	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}

			public Object[] getElements(Object inputElement) {
				return (subject() == null) ?
						new String[] {}:
						CollectionTools.array(PersistentTypeDetailsPage.this.typeMappingUiProviders());
			}

			public void inputChanged(
					Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}

	private PropertyAspectAdapter<PersistentType, TypeMapping> buildGenericMappingHolder() {
		return new PropertyAspectAdapter<PersistentType, TypeMapping>(getSubjectHolder(), PersistentType.MAPPING_PROPERTY) {
			@Override
			protected TypeMapping buildValue_() {
				return subject.getMapping();
			}
		};
	}

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((TypeMappingUiProvider<?>) element).label();
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected JpaComposite<TypeMapping> buildMappingComposite(PageBook pageBook,
	                                                            String key)  {

		TypeMappingUiProvider<TypeMapping> uiProvider =
			(TypeMappingUiProvider<TypeMapping>) typeMappingUiProvider(key);

		return uiProvider.buildPersistentTypeMappingComposite(
			buildMappingHolder(key),
			pageBook,
			getFormWidgetFactory()
		);
	}

	private Filter<TypeMapping> buildMappingFilter(final String key) {
		return new Filter<TypeMapping>() {
			public boolean accept(TypeMapping value) {
				return (value == null) || key.equals(value.getKey());
			}
		};
	}

	private PropertyValueModel<TypeMapping> buildMappingHolder(String key) {
		return new FilteringPropertyValueModel<TypeMapping>(
			buildGenericMappingHolder(),
			buildMappingFilter(key)
		);
	}

	protected ComboViewer buildTypeMappingCombo(Composite parent) {
		CCombo combo = buildCCombo(parent);
		this.typeMappingCombo = new ComboViewer(combo);
		this.typeMappingCombo.getCCombo().setVisibleItemCount(Integer.MAX_VALUE);
		this.typeMappingCombo.setContentProvider(buildContentProvider());
		this.typeMappingCombo.setLabelProvider(buildLabelProvider());
		this.typeMappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				typeMappingChanged(event);
			}
		});
		return this.typeMappingCombo;
	}

	protected Label buildTypeMappingLabel(Composite parent) {
		return buildLabel(parent, JptUiMessages.PersistentTypePage_mapAs);
	}

	protected PageBook buildTypeMappingPageBook(Composite parent) {
		this.typeMappingPageBook = new PageBook(parent, SWT.NONE);
		return this.typeMappingPageBook;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doDispose() {
		log(Tracing.UI_DETAILS_VIEW, "PersistentTypeDetailsPage.doDispose()");

		if (this.currentMappingComposite != null) {
			this.currentMappingComposite.dispose();
			this.currentMappingComposite = null;
		}

		this.mappingComposites.clear();
		super.doDispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateMappingComboAndPage();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.mappingComposites = new HashMap<String, JpaComposite<TypeMapping>>();
	}

	private JpaComposite<TypeMapping> mappingCompositeFor(String key) {
		JpaComposite<TypeMapping> mappingComposite = this.mappingComposites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}

		mappingComposite = buildMappingComposite(this.typeMappingPageBook, key);

		if (mappingComposite != null) {
			this.mappingComposites.put(key, mappingComposite);
		}

		return mappingComposite;
	}

	private void populateMapAsCombo() {
		if (this.subject() != this.typeMappingCombo.getInput()) {
			this.typeMappingCombo.setInput(this.subject());
		}
		if (this.subject() != null) {
			if (this.subject().getMapping() == null) {
				this.typeMappingCombo.setSelection(new StructuredSelection(this.typeMappingCombo.getElementAt(0)));
			}
			else {
				TypeMappingUiProvider<? extends TypeMapping> provider = typeMappingUiProvider(this.subject().mappingKey());
				if (provider != null && ! provider.equals(((StructuredSelection) this.typeMappingCombo.getSelection()).getFirstElement())) {
					this.typeMappingCombo.setSelection(new StructuredSelection(provider));
				}
			}
		}
	}

	private void populateMappingComboAndPage() {
		populateMapAsCombo();
		updateMappingPage();
	}

	private void populateMappingPage(String mappingKey) {

		// Nothing to update
		if (this.currentMappingKey == mappingKey) {
			return;
		}
		// Dispose the existing mapping pane
		else if (this.currentMappingComposite != null) {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentTypeDetailsPage.populateMappingPage() disposing of current page: " + this.currentMappingKey
			);

			try {
				this.currentMappingComposite.dispose();
				this.currentMappingComposite = null;
			}
			catch (Exception e) {
				JptUiPlugin.log(e);
			}
		}

		this.currentMappingKey = mappingKey;

		// Change the current mapping pane with the new one
		if (this.currentMappingKey != null) {
			this.currentMappingComposite = mappingCompositeFor(mappingKey);

			// Show the new mapping pane
			try {
				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentTypeDetailsPage.populateMappingPage() populating new page: " + this.currentMappingKey
				);

				this.currentMappingComposite.populate();
				this.typeMappingPageBook.showPage(this.currentMappingComposite.getControl());
				this.repaintDetailsView(this.typeMappingPageBook);
			}
			catch (Exception e) {
				JptUiPlugin.log(e);

				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentTypeDetailsPage.populateMappingPage() error encountered"
				);

				// An error was encountered either during the population, dispose it
				try {
					this.currentMappingComposite.dispose();
				}
				catch (Exception exception) {
					JptUiPlugin.log(e);
				}

				this.mappingComposites.remove(this.currentMappingComposite);
				this.currentMappingComposite = null;

				// Show an error message
				// TODO: Replace the blank label with the error page
				this.typeMappingPageBook.showPage(new Label(this.typeMappingPageBook, SWT.NULL));
			}
		}
		// Clear the mapping pane and show a blank page
		else {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentTypeDetailsPage.populateMappingPage() no page to show"
			);

			this.typeMappingPageBook.showPage(new Label(this.typeMappingPageBook, SWT.NULL));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentType.MAPPING_PROPERTY) {
			populateMappingComboAndPage();
		}
	}

	private void typeMappingChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof StructuredSelection) {
			TypeMappingUiProvider<?> provider = (TypeMappingUiProvider<?>) ((StructuredSelection) event.getSelection()).getFirstElement();
			this.subject().setMappingKey(provider.mappingKey());
		}
	}

	private TypeMappingUiProvider<? extends TypeMapping> typeMappingUiProvider(String key) {
		for (ListIterator<TypeMappingUiProvider<? extends TypeMapping>> iter = this.typeMappingUiProviders(); iter.hasNext();) {
			TypeMappingUiProvider<? extends TypeMapping> provider = iter.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key);
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return typeMappingCombo.getCombo().setFocus();
//	}

	protected abstract ListIterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders();

	private void updateMappingPage() {
		TypeMapping mapping = (this.subject() != null) ? this.subject().getMapping() : null;
		populateMappingPage(mapping == null ? null : mapping.getKey());
	}
}
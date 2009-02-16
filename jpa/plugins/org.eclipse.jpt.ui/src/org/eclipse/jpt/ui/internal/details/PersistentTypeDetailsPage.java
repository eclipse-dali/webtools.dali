/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
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
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.mappings.details.PersistentTypeMapAsComposite;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent type.
 *
 * @see PersistentType
 *
 * @version 2.2
 * @since 1.0
 */
@SuppressWarnings("nls")
public class PersistentTypeDetailsPage extends AbstractJpaDetailsPage<PersistentType>
{
	private JpaComposite currentMappingComposite;
	private String currentMappingKey;
	private Map<String, JpaComposite> mappingComposites;
	private PageBook typeMappingPageBook;

	/**
	 * Creates a new <code>PersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistentTypeDetailsPage(Composite parent,
                                    WidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.mappingComposites = new HashMap<String, JpaComposite>();
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		// Map As composite
		new PersistentTypeMapAsComposite(
			this,
			addSubPane(container, 0, 0, 5, 0)
		);

		// Type properties page
		buildTypeMappingPageBook(container);
	}

	protected PageBook buildTypeMappingPageBook(Composite parent) {

		this.typeMappingPageBook = new PageBook(parent, SWT.NONE);
		this.typeMappingPageBook.showPage(this.addLabel(this.typeMappingPageBook, ""));

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		this.typeMappingPageBook.setLayoutData(gridData);

		return this.typeMappingPageBook;
	}

	private TypeMappingUiProvider<? extends TypeMapping> typeMappingUiProvider(String key) {
		for (Iterator<TypeMappingUiProvider<? extends TypeMapping>> iter = this.typeMappingUiProviders(); iter.hasNext();) {
			TypeMappingUiProvider<? extends TypeMapping> provider = iter.next();
			if (provider.getKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key);
	}

	protected Iterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		return getJpaPlatformUi().typeMappingUiProviders(getSubject().getContentType());
	}

	private PropertyValueModel<TypeMapping> buildMappingHolder(String key) {
		return new FilteringPropertyValueModel<TypeMapping>(
			buildGenericMappingHolder(),
			buildMappingFilter(key)
		);
	}

	private PropertyAspectAdapter<PersistentType, TypeMapping> buildGenericMappingHolder() {
		return new PropertyAspectAdapter<PersistentType, TypeMapping>(getSubjectHolder(), PersistentType.MAPPING_PROPERTY) {
			@Override
			protected TypeMapping buildValue_() {
				return this.subject.getMapping();
			}
		};
	}

	private Filter<TypeMapping> buildMappingFilter(final String key) {
		return new Filter<TypeMapping>() {
			public boolean accept(TypeMapping value) {
				return (value == null) || key.equals(value.getKey());
			}
		};
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
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
			this.currentMappingComposite = getMappingCompositeFor(mappingKey);

			// Show the new mapping pane
			try {
				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentTypeDetailsPage.populateMappingPage() populating new page: " + this.currentMappingKey
				);

				this.currentMappingComposite.populate();
				this.typeMappingPageBook.showPage(this.currentMappingComposite.getControl());
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
				this.typeMappingPageBook.showPage(this.addLabel(this.typeMappingPageBook, ""));
			}
		}
		// Clear the mapping pane and show a blank page
		else {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentTypeDetailsPage.populateMappingPage() no page to show"
			);

			this.typeMappingPageBook.showPage(this.addLabel(this.typeMappingPageBook, ""));
		}
		this.repaintDetailsView(this.typeMappingPageBook);
	}
	
	private JpaComposite getMappingCompositeFor(String key) {
		JpaComposite mappingComposite = this.mappingComposites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}

		mappingComposite = buildMappingComposite(this.typeMappingPageBook, key);

		if (mappingComposite != null) {
			this.mappingComposites.put(key, mappingComposite);
		}

		return mappingComposite;
	}
	
	@SuppressWarnings("unchecked")
	protected JpaComposite buildMappingComposite(PageBook pageBook,
	                                                            String key)  {
//		return getJpaPlatformUi().buildPersistentTypeMappingComposite(
//			buildMappingHolder(key),
//			pageBook,
//			getWidgetFactory());
		TypeMappingUiProvider<TypeMapping> uiProvider =
			(TypeMappingUiProvider<TypeMapping>) typeMappingUiProvider(key);

		return uiProvider.buildPersistentTypeMappingComposite(
			getJpaUiFactory(),
			buildMappingHolder(key),
			pageBook,
			getWidgetFactory()
		);
	}	

	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(PersistentType.MAPPING_PROPERTY);
	}

	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == PersistentType.MAPPING_PROPERTY) {
			updateMappingPage();
		}
	}

	private void updateMappingPage() {
		TypeMapping mapping = (this.getSubject() != null) ? this.getSubject().getMapping() : null;
		populateMappingPage(mapping == null ? null : mapping.getKey());
	}

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

}
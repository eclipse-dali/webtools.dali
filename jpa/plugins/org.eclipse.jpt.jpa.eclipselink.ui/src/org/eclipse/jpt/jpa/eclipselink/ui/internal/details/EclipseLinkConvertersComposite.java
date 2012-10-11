/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkConverterDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * This pane shows the list of custom converters, object type converters,
 * struct converters, and type converters.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ConverterComposite or ObjectTypeConverterComposite                    | |
 * | | or StructConverterComposite or TypeConverterComposite                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 3.2
 * @since 2.1
 */
public class EclipseLinkConvertersComposite extends Pane<EclipseLinkConverterContainer>
{
	private EclipseLinkCustomConverterComposite converterComposite;
	private EclipseLinkObjectTypeConverterComposite objectTypeConverterComposite;
	private EclipseLinkStructConverterComposite structConverterComposite;
	private EclipseLinkTypeConverterComposite typeConverterComposite;
	private ModifiableCollectionValueModel<EclipseLinkConverter> selectedConvertersModel;
	private PropertyValueModel<EclipseLinkConverter> selectedConverterModel;

	public EclipseLinkConvertersComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkConverterContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedConvertersModel = this.buildSelectedConvertersModel();
		this.selectedConverterModel = this.buildSelectedConverterModel(this.selectedConvertersModel);
	}

	private ModifiableCollectionValueModel<EclipseLinkConverter> buildSelectedConvertersModel() {
		return new SimpleCollectionValueModel<EclipseLinkConverter>();
	}

	protected PropertyValueModel<EclipseLinkConverter> buildSelectedConverterModel(ModifiableCollectionValueModel<EclipseLinkConverter> selectedConvertersModel) {
		return new CollectionPropertyValueModelAdapter<EclipseLinkConverter, EclipseLinkConverter>(selectedConvertersModel) {
			@Override
			protected EclipseLinkConverter buildValue() {
				if (this.collectionModel.size() == 1) {
					return this.collectionModel.iterator().next();
				}
				return null;
			}
		};
	}

	
	@Override
	protected void initializeLayout(Composite container) {
		// List pane
		this.addListPane(container);

		// Property pane
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		installPaneSwitcher(pageBook);
	}

	protected EclipseLinkCustomConverterComposite getCustomConverterComposite(PageBook pageBook) {
		if (this.converterComposite == null) {
			this.converterComposite = new EclipseLinkCustomConverterComposite(
				buildSelectedCustomConverterModel(),
				pageBook,
				getWidgetFactory()
			);
			registerSubPane(this.converterComposite);
		}
		return this.converterComposite;
	}

	protected EclipseLinkObjectTypeConverterComposite getObjectTypeConverterComposite(PageBook pageBook) {
		if (this.objectTypeConverterComposite == null) {
			this.objectTypeConverterComposite = new EclipseLinkObjectTypeConverterComposite(
				buildSelectedObjectTypeConverterModel(),
				pageBook,
				getWidgetFactory()
			);
			registerSubPane(this.objectTypeConverterComposite);
		}
		return this.objectTypeConverterComposite;
	}
		
	protected EclipseLinkStructConverterComposite getStructConverterComposite(PageBook pageBook) {
		if (this.structConverterComposite == null) {
			this.structConverterComposite = new EclipseLinkStructConverterComposite(
				buildSelectedStructConverterModel(),
				pageBook,
				getWidgetFactory()
			);
			registerSubPane(this.structConverterComposite);
		}
		return this.structConverterComposite;
	}

	protected EclipseLinkTypeConverterComposite getTypeConverterComposite(PageBook pageBook) {
		if (this.typeConverterComposite == null) {
			this.typeConverterComposite = new EclipseLinkTypeConverterComposite(
				buildSelectedTypeConverterModel(),
				pageBook,
				getWidgetFactory()
			);
			registerSubPane(this.typeConverterComposite);
		}
		return this.typeConverterComposite;
	}

	private AddRemoveListPane<EclipseLinkConverterContainer, EclipseLinkConverter> addListPane(Composite container) {

		return new AddRemoveListPane<EclipseLinkConverterContainer, EclipseLinkConverter>(
			this,
			container,
			buildConvertersAdapter(),
			buildDisplayableConvertersListHolder(),
			this.selectedConvertersModel,
			buildConvertersListLabelProvider(),
			null
		);
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(this.selectedConverterModel, this.buildPaneTransformer(pageBook), pageBook);
	}
	
	private Adapter<EclipseLinkConverter> buildConvertersAdapter() {

		return new AddRemoveListPane.AbstractAdapter<EclipseLinkConverter>() {

			public EclipseLinkConverter addNewItem() {
				return addConverter();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<EclipseLinkConverter> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<EclipseLinkConverter> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				EclipseLinkConverter item = selectedItemsModel.iterator().next();
				if (item.getType() == EclipseLinkCustomConverter.class) {
					getSubject().removeCustomConverter((EclipseLinkCustomConverter) item);
				}
				else if (item.getType() == EclipseLinkObjectTypeConverter.class) {
					getSubject().removeObjectTypeConverter((EclipseLinkObjectTypeConverter) item);
				}
				else if (item.getType() == EclipseLinkStructConverter.class) {
					getSubject().removeStructConverter((EclipseLinkStructConverter) item);
				}
				else if (item.getType() == EclipseLinkTypeConverter.class) {
					getSubject().removeTypeConverter((EclipseLinkTypeConverter) item);
				}
			}
		};
	}

	private EclipseLinkConverter addConverter() {
		return this.addEclipseLinkConverterFromDialog(this.buildEclipseLinkConverterDialog());
	}

	protected EclipseLinkConverterDialog buildEclipseLinkConverterDialog() {
		return new EclipseLinkConverterDialog(this.getShell(), this.getSubject());
	}

	protected EclipseLinkConverter addEclipseLinkConverterFromDialog(EclipseLinkConverterDialog dialog) {
		if (dialog.open() != Window.OK) {
			return null;
		}
		Class<? extends EclipseLinkConverter> converterType = dialog.getConverterType();
		EclipseLinkConverter converter;
		if (converterType == EclipseLinkCustomConverter.class) {
			converter = this.getSubject().addCustomConverter(getSubject().getCustomConvertersSize());
		}
		else if (converterType == EclipseLinkObjectTypeConverter.class) {
			converter = this.getSubject().addObjectTypeConverter(getSubject().getObjectTypeConvertersSize());
		}
		else if (converterType == EclipseLinkStructConverter.class) {
			converter = this.getSubject().addStructConverter(getSubject().getStructConvertersSize());
		}
		else if (converterType == EclipseLinkTypeConverter.class) {
			converter = this.getSubject().addTypeConverter(getSubject().getTypeConvertersSize());
		}
		else {
			throw new IllegalArgumentException();
		}
		converter.setName(dialog.getName());

		return converter;
	}

	private Transformer<EclipseLinkConverter, Control> buildPaneTransformer(final PageBook pageBook) {
		return new Transformer<EclipseLinkConverter, Control>() {
			public Control transform(EclipseLinkConverter converter) {
				if (converter == null) {
					return null;
				}

				if (converter.getType() == EclipseLinkCustomConverter.class) {
					return EclipseLinkConvertersComposite.this.getCustomConverterComposite(pageBook).getControl();
				}
				if (converter.getType() == EclipseLinkObjectTypeConverter.class) {
					return EclipseLinkConvertersComposite.this.getObjectTypeConverterComposite(pageBook).getControl();
				}
				if (converter.getType() == EclipseLinkStructConverter.class) {
					return EclipseLinkConvertersComposite.this.getStructConverterComposite(pageBook).getControl();
				}
				if (converter.getType() == EclipseLinkTypeConverter.class) {
					return EclipseLinkConvertersComposite.this.getTypeConverterComposite(pageBook).getControl();
				}

				return null;
			}
		};
	}

	private ListValueModel<EclipseLinkConverter> buildDisplayableConvertersListHolder() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConverter>(
			buildEclipseLinkConvertersHolder(),
			JpaNamedContextNode.NAME_PROPERTY
		);
	}

	private ListValueModel<EclipseLinkConverter> buildEclipseLinkConvertersHolder() {
		List<ListValueModel<? extends EclipseLinkConverter>> list = new ArrayList<ListValueModel<? extends EclipseLinkConverter>>();
		list.add(buildCustomConvertersListHolder());
		list.add(buildObjectTypeConvertersListHolder());
		list.add(buildStructConvertersListHolder());
		list.add(buildTypeConvertersListHolder());
		return CompositeListValueModel.forModels(list);
	}

	private ListValueModel<EclipseLinkCustomConverter> buildCustomConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkCustomConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.CUSTOM_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkCustomConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkCustomConverter>(this.subject.getCustomConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getCustomConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkObjectTypeConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.OBJECT_TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkObjectTypeConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkObjectTypeConverter>(this.subject.getObjectTypeConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getObjectTypeConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkStructConverter> buildStructConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkStructConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.STRUCT_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkStructConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkStructConverter>(this.subject.getStructConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getStructConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkTypeConverter> buildTypeConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkTypeConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkTypeConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkTypeConverter>(this.subject.getTypeConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getTypeConvertersSize();
			}
		};
	}

	private PropertyValueModel<EclipseLinkCustomConverter> buildSelectedCustomConverterModel() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkCustomConverter>(this.selectedConverterModel) {
			@Override
			protected EclipseLinkCustomConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkCustomConverter.class ? (EclipseLinkCustomConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkObjectTypeConverter> buildSelectedObjectTypeConverterModel() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkObjectTypeConverter>(this.selectedConverterModel) {
			@Override
			protected EclipseLinkObjectTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkObjectTypeConverter.class ? (EclipseLinkObjectTypeConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkStructConverter> buildSelectedStructConverterModel() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkStructConverter>(this.selectedConverterModel) {
			@Override
			protected EclipseLinkStructConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkStructConverter.class ? (EclipseLinkStructConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkTypeConverter> buildSelectedTypeConverterModel() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkTypeConverter>(this.selectedConverterModel) {
			@Override
			protected EclipseLinkTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkTypeConverter.class ? (EclipseLinkTypeConverter) value : null;
			}
		};
	}

	private ILabelProvider buildConvertersListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((EclipseLinkConverter) element).getName();
			}
		};
	}
}
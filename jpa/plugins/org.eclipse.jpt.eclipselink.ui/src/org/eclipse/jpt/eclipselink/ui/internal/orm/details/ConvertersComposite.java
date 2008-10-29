/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.ConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.CustomConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ObjectTypeConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.StructConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.TypeConverterComposite;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * This pane shows the list of named queries and named native queries.
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
 * @see Entity
 * @see Query
 * @see NamedNativeQuery
 * @see NamedQuery
 * @see AbstractEntityComposite - The parent container
 * @see NamedNativeQueryPropertyComposite
 * @see NamedQueryPropertyComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class ConvertersComposite extends Pane<ConverterHolder>
{
	private AddRemoveListPane<ConverterHolder> listPane;
	private CustomConverterComposite converterComposite;
	private ObjectTypeConverterComposite objectTypeConverterComposite;
	private StructConverterComposite structConverterComposite;
	private TypeConverterComposite typeConverterComposite;
	private WritablePropertyValueModel<EclipseLinkConverter> selectedConverterHolder;

	public ConvertersComposite(
		FormPane<?> parentPane, 
		PropertyValueModel<? extends ConverterHolder> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedConverterHolder = buildSelectedConverterHolder();
	}

	private WritablePropertyValueModel<EclipseLinkConverter> buildSelectedConverterHolder() {
		return new SimplePropertyValueModel<EclipseLinkConverter>();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		this.listPane = addListPane(container);

		// Property pane
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.converterComposite = new CustomConverterComposite(
			buildCustomConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		
		this.objectTypeConverterComposite = new ObjectTypeConverterComposite(
			buildObjectTypeConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		
		this.structConverterComposite = new StructConverterComposite(
			buildStructConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		
		this.typeConverterComposite = new TypeConverterComposite(
			buildTypeConverterHolder(),
			pageBook,
			getWidgetFactory()
		);

		installPaneSwitcher(pageBook);
	}

	private AddRemoveListPane<ConverterHolder> addListPane(Composite container) {

		return new AddRemoveListPane<ConverterHolder>(
			this,
			container,
			buildConvertersAdapter(),
			buildDisplayableConvertersListHolder(),
			this.selectedConverterHolder,
			buildConvertersListLabelProvider(),
			null//JpaHelpContextIds.MAPPING_NAMED_QUERIES
		) {
			//TODO yeah, this is weird, but i don't want this to be disabled just
			//because the subject is null. i have no need for that and that is
			//currently how AddRemovePane works.  See OrmQueriesComposite where
			//the work around there is yet another pane enabler.  I want to change
			//how this works in 2.2
			@Override
			public void enableWidgets(boolean enabled) {
				super.enableWidgets(true);
			}
		};
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(this.selectedConverterHolder, buildPaneTransformer(), pageBook);
	}
	
	private Adapter buildConvertersAdapter() {

		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addConverter(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
						getSubject().removeCustomConverter((CustomConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
						getSubject().removeObjectTypeConverter((ObjectTypeConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
						getSubject().removeStructConverter((StructConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.TYPE_CONVERTER) {
						getSubject().removeTypeConverter((TypeConverter) item);
					}
				}
			}
		};
	}

	private void addConverter(ObjectListSelectionModel listSelectionModel) {
		EclipseLinkConverterDialog dialog = buildEclipseLinkConverterDialog();
		addEclipseLinkConverterFromDialog(dialog, listSelectionModel);
	}
	
	protected EclipseLinkConverterDialog buildEclipseLinkConverterDialog() {
		return new EclipseLinkConverterDialog(getControl().getShell());
	}

	protected void addEclipseLinkConverterFromDialog(EclipseLinkConverterDialog dialog, ObjectListSelectionModel listSelectionModel) {
		if (dialog.open() != Window.OK) {
			return;
		}
		String converterType = dialog.getConverterType();
		EclipseLinkConverter converter;
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			converter = this.getSubject().addCustomConverter(getSubject().customConvertersSize());
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			converter = this.getSubject().addObjectTypeConverter(getSubject().objectTypeConvertersSize());
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			converter = this.getSubject().addStructConverter(getSubject().structConvertersSize());
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			converter = this.getSubject().addTypeConverter(getSubject().typeConvertersSize());
		}
		else {
			throw new IllegalArgumentException();
		}
		converter.setName(dialog.getName());

		listSelectionModel.setSelectedValue(converter);
	}

	private Transformer<EclipseLinkConverter, Control> buildPaneTransformer() {
		return new Transformer<EclipseLinkConverter, Control>() {
			public Control transform(EclipseLinkConverter converter) {
				if (converter == null) {
					return null;
				}

				if (converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
					return ConvertersComposite.this.converterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
					return ConvertersComposite.this.objectTypeConverterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
					return ConvertersComposite.this.structConverterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) {
					return ConvertersComposite.this.typeConverterComposite.getControl();
				}

				return null;
			}
		};
	}

	private ListValueModel<EclipseLinkConverter> buildDisplayableConvertersListHolder() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConverter>(
			buildEclipseLinkConvertersHolder(),
			EclipseLinkConverter.NAME_PROPERTY
		);
	}

	private ListValueModel<EclipseLinkConverter> buildEclipseLinkConvertersHolder() {
		List<ListValueModel<? extends EclipseLinkConverter>> list = new ArrayList<ListValueModel<? extends EclipseLinkConverter>>();
		list.add(buildCustomConvertersListHolder());
		list.add(buildObjectTypeConvertersListHolder());
		list.add(buildStructConvertersListHolder());
		list.add(buildTypeConvertersListHolder());
		return new CompositeListValueModel<ListValueModel<? extends EclipseLinkConverter>, EclipseLinkConverter>(list);
	}

	private ListValueModel<CustomConverter> buildCustomConvertersListHolder() {
		return new ListAspectAdapter<ConverterHolder, CustomConverter>(
			getSubjectHolder(),
			ConverterHolder.CUSTOM_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<CustomConverter> listIterator_() {
				return this.subject.customConverters();
			}

			@Override
			protected int size_() {
				return this.subject.customConvertersSize();
			}
		};
	}

	private ListValueModel<ObjectTypeConverter> buildObjectTypeConvertersListHolder() {
		return new ListAspectAdapter<ConverterHolder, ObjectTypeConverter>(
			getSubjectHolder(),
			ConverterHolder.OBJECT_TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<ObjectTypeConverter> listIterator_() {
				return this.subject.objectTypeConverters();
			}

			@Override
			protected int size_() {
				return this.subject.objectTypeConvertersSize();
			}
		};
	}

	private ListValueModel<StructConverter> buildStructConvertersListHolder() {
		return new ListAspectAdapter<ConverterHolder, StructConverter>(
			getSubjectHolder(),
			ConverterHolder.STRUCT_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<StructConverter> listIterator_() {
				return this.subject.structConverters();
			}

			@Override
			protected int size_() {
				return this.subject.structConvertersSize();
			}
		};
	}

	private ListValueModel<TypeConverter> buildTypeConvertersListHolder() {
		return new ListAspectAdapter<ConverterHolder, TypeConverter>(
			getSubjectHolder(),
			ConverterHolder.TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<TypeConverter> listIterator_() {
				return this.subject.typeConverters();
			}

			@Override
			protected int size_() {
				return this.subject.typeConvertersSize();
			}
		};
	}

	private PropertyValueModel<CustomConverter> buildCustomConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, CustomConverter>(this.selectedConverterHolder) {
			@Override
			protected CustomConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.CUSTOM_CONVERTER ? (CustomConverter) value : null;
			}
		};
	}

	private PropertyValueModel<ObjectTypeConverter> buildObjectTypeConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, ObjectTypeConverter>(this.selectedConverterHolder) {
			@Override
			protected ObjectTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER ? (ObjectTypeConverter) value : null;
			}
		};
	}

	private PropertyValueModel<StructConverter> buildStructConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, StructConverter>(this.selectedConverterHolder) {
			@Override
			protected StructConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.STRUCT_CONVERTER ? (StructConverter) value : null;
			}
		};
	}

	private PropertyValueModel<TypeConverter> buildTypeConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, TypeConverter>(this.selectedConverterHolder) {
			@Override
			protected TypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.TYPE_CONVERTER ? (TypeConverter) value : null;
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

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.listPane.enableWidgets(enabled);
	}

	//TODO need to check the converter repository for this, should check all converters, except for the override case, hmm
	//we at least need to check typeconverters, converters, structconverters, and objectypeconverters, on this particular
	//object.  or we need to give a warning about the case where you are overriding or an error if it's not an override?
	private Iterator<String> converterNames() {
		return new TransformationIterator<CustomConverter, String>(getSubject().customConverters()) {
			@Override
			protected String transform(CustomConverter next) {
				return next.getName();
			}
		};
	}
}
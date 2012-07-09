/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringConverter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Name:      |                                                             ||
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConverter
 * @see EclipseLinkConvertCombo - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkObjectTypeConverterComposite extends Pane<EclipseLinkObjectTypeConverter>
{

	/**
	 * Creates a new <code>ObjectTypeConverterComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkObjectTypeConverterComposite(PropertyValueModel<? extends EclipseLinkObjectTypeConverter> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkConverterComposite_nameTextLabel);
		this.addText(container, buildNameTextHolder());
		
		Hyperlink dataTypeHyperlink = this.addHyperlink(container, EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_dataTypeLabel);
		this.addDataTypeChooser(container, dataTypeHyperlink);
		
		Hyperlink objectTypeHyperlink = this.addHyperlink(container,  EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_objectTypeLabel);
		this.addObjectTypeChooser(container, objectTypeHyperlink);
		
		Composite conversionValuesTable = addConversionValuesTable(container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		conversionValuesTable.setLayoutData(gridData);

		this.addLabel(container, EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_defaultObjectValueLabel);
		this.addEditableCombo(
			container,
			buildDefaultObjectValueListHolder(),
			buildDefaultObjectValueHolder(),
			buildStringConverter(),
			(String) null
		);
	}
	
	protected ModifiablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(
				getSubjectHolder(), JpaNamedContextNode.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}
		
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setName(value);
			}
		};
	}
	
	private ClassChooserPane<EclipseLinkObjectTypeConverter> addDataTypeChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<EclipseLinkObjectTypeConverter>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(getSubjectHolder(), EclipseLinkObjectTypeConverter.DATA_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getDataType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setDataType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getDataType();
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setDataType(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedDataType();
			}
		};
	}

	private ClassChooserPane<EclipseLinkObjectTypeConverter> addObjectTypeChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<EclipseLinkObjectTypeConverter>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(getSubjectHolder(), EclipseLinkObjectTypeConverter.OBJECT_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getObjectType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setObjectType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getObjectType();
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setObjectType(className);
			}
			
			@Override
			protected int getTypeDialogStyle() {
				return IJavaElementSearchConstants.CONSIDER_CLASSES_AND_ENUMS;
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedObjectType();
			}
		};
	}
	
	protected Composite addConversionValuesTable(Composite container) {
		// Join Columns group pane
		Group conversionValuesGroupPane = addTitledGroup(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValuesGroupTitle
		);

		// Conversion Values add/remove list pane
		new AddRemoveTablePane<EclipseLinkObjectTypeConverter, EclipseLinkConversionValue>(
			this,
			conversionValuesGroupPane,
			buildConversionValuesAdapter(),
			buildConversionValuesListModel(),
			buildSelectedConversionValuesModel(),
			buildConversionValuesLabelProvider(),
			null//TODO need a help context id for this
		) {
			@Override
			protected ColumnAdapter<EclipseLinkConversionValue> buildColumnAdapter() {
				return new ConversionValueColumnAdapter();
			}
		};

		return conversionValuesGroupPane;
	}

	private ModifiableCollectionValueModel<EclipseLinkConversionValue> buildSelectedConversionValuesModel() {
		return new SimpleCollectionValueModel<EclipseLinkConversionValue>();
	}

	protected AddRemoveListPane.Adapter<EclipseLinkConversionValue> buildConversionValuesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<EclipseLinkConversionValue>() {

			public EclipseLinkConversionValue addNewItem() {
				EclipseLinkConversionValueDialog dialog = buildConversionValueDialogForAdd();
				return addConversionValueFromDialog(dialog);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValueEdit;
			}

			@Override
			public void optionOnSelection(CollectionValueModel<EclipseLinkConversionValue> selectedItemsModel) {
				EclipseLinkConversionValue conversionValue = selectedItemsModel.iterator().next();
				EclipseLinkConversionValueDialog dialog = new EclipseLinkConversionValueDialog(getShell(), getSubject(), conversionValue);
				editConversionValueFromDialog(dialog, conversionValue);
			}


			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<EclipseLinkConversionValue> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<EclipseLinkConversionValue> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				EclipseLinkConversionValue item =  selectedItemsModel.iterator().next();
				getSubject().removeConversionValue(item);
			}
		};
	}


	protected EclipseLinkConversionValueDialog buildConversionValueDialogForAdd() {
		return new EclipseLinkConversionValueDialog(getShell(), getSubject());
	}

	protected EclipseLinkConversionValue addConversionValueFromDialog(EclipseLinkConversionValueDialog dialog) {
		if (dialog.open() != Window.OK) {
			return null;
		}

		EclipseLinkConversionValue conversionValue = this.getSubject().addConversionValue();
		conversionValue.setDataValue(dialog.getDataValue());
		conversionValue.setObjectValue(dialog.getObjectValue());

		return conversionValue;
	}

	protected void editConversionValueFromDialog(EclipseLinkConversionValueDialog dialog, EclipseLinkConversionValue conversionValue) {
		if (dialog.open() != Window.OK) {
			return;
		}

		conversionValue.setDataValue(dialog.getDataValue());
		conversionValue.setObjectValue(dialog.getObjectValue());
	}
	
	private ListValueModel<EclipseLinkConversionValue> buildConversionValuesListModel() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConversionValue>(buildConversionValuesListHolder(), 
			EclipseLinkConversionValue.DATA_VALUE_PROPERTY,
			EclipseLinkConversionValue.OBJECT_VALUE_PROPERTY);
	}	

	private ListValueModel<EclipseLinkConversionValue> buildConversionValuesListHolder() {
		return new ListAspectAdapter<EclipseLinkObjectTypeConverter, EclipseLinkConversionValue>(getSubjectHolder(), EclipseLinkObjectTypeConverter.CONVERSION_VALUES_LIST) {
			@Override
			protected ListIterable<EclipseLinkConversionValue> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkConversionValue>(this.subject.getConversionValues());
			}

			@Override
			protected int size_() {
				return this.subject.getConversionValuesSize();
			}
		};
	}

	private ITableLabelProvider buildConversionValuesLabelProvider() {
		return new TableLabelProvider();
	}
	
	protected ListValueModel<String> buildDefaultObjectValueListHolder() {
		return new TransformationListValueModel<EclipseLinkConversionValue, String>(buildConversionValuesListModel()) {
			@Override
			protected String transformItem(EclipseLinkConversionValue conversionValue) {
				return conversionValue.getObjectValue();
			}
		};
	}
	
	protected ModifiablePropertyValueModel<String> buildDefaultObjectValueHolder() {
		return new PropertyAspectAdapter<EclipseLinkObjectTypeConverter, String>(
				getSubjectHolder(), EclipseLinkObjectTypeConverter.DEFAULT_OBJECT_VALUE_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getDefaultObjectValue();
			}
		
			@Override
			protected void setValue_(String value) {
				if (value != null && value.length() == 0) {
					value = null;
				}
				this.subject.setDefaultObjectValue(value);
			}
		};
	}
	
	protected final StringConverter<String> buildStringConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {
				return (value == null) ? "" : value; //$NON-NLS-1$
			}
		};
	}
	
	private class TableLabelProvider extends LabelProvider
		implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
		
			EclipseLinkConversionValue conversionValue = (EclipseLinkConversionValue) element;
			String value = null;
			
			switch (columnIndex) {
				case ConversionValueColumnAdapter.DATA_VALUE_COLUMN: {
					value = conversionValue.getDataValue();
					break;
				}
			
				case ConversionValueColumnAdapter.OBJECT_VALUE_COLUMN: {
					value = conversionValue.getObjectValue();
					break;
				}
			}
			
			if (value == null) {
				value = ""; //$NON-NLS-1$
			}
		
			return value;
		}
	}

	private static class ConversionValueColumnAdapter implements ColumnAdapter<EclipseLinkConversionValue> {

		public static final int COLUMN_COUNT = 2;
		//public static final int SELECTION_COLUMN = 0;
		public static final int DATA_VALUE_COLUMN = 0;
		public static final int OBJECT_VALUE_COLUMN = 1;

		private ModifiablePropertyValueModel<String> buildDataValueHolder(EclipseLinkConversionValue subject) {
			return new PropertyAspectAdapter<EclipseLinkConversionValue, String>(EclipseLinkConversionValue.DATA_VALUE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return this.subject.getDataValue();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setDataValue(value);
				}
			};
		}

		private ModifiablePropertyValueModel<String> buildObjectValueHolder(EclipseLinkConversionValue subject) {
			return new PropertyAspectAdapter<EclipseLinkConversionValue, String>(EclipseLinkConversionValue.OBJECT_VALUE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return this.subject.getObjectValue();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setObjectValue(value);
				}
			};
		}

		public ModifiablePropertyValueModel<?>[] cellModels(EclipseLinkConversionValue subject) {
			ModifiablePropertyValueModel<?>[] holders = new ModifiablePropertyValueModel<?>[COLUMN_COUNT];
			//holders[SELECTION_COLUMN] = new SimplePropertyValueModel<Object>();
			holders[DATA_VALUE_COLUMN]      = buildDataValueHolder(subject);
			holders[OBJECT_VALUE_COLUMN]     = buildObjectValueHolder(subject);
			return holders;
		}

		public int columnCount() {
			return COLUMN_COUNT;
		}

		public String columnName(int columnIndex) {

			switch (columnIndex) {
				case ConversionValueColumnAdapter.DATA_VALUE_COLUMN: {
					return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValuesDataValueColumn;
				}

				case ConversionValueColumnAdapter.OBJECT_VALUE_COLUMN: {
					return EclipseLinkUiDetailsMessages.EclipseLinkObjectTypeConverterComposite_conversionValuesObjectValueColumn;
				}

				default: {
					return null;
				}
			}
		}
	}
}

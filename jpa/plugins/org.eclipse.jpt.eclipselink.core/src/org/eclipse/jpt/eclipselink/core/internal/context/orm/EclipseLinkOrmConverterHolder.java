/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class EclipseLinkOrmConverterHolder extends AbstractXmlContextNode implements ConverterHolder
{	
	private XmlConvertersHolder resourceConvertersHolder;
	
	protected final List<EclipseLinkOrmCustomConverter> customConverters;
	protected final List<EclipseLinkOrmObjectTypeConverter> objectTypeConverters;
	protected final List<EclipseLinkOrmStructConverter> structConverters;
	protected final List<EclipseLinkOrmTypeConverter> typeConverters;
	
	public EclipseLinkOrmConverterHolder(XmlContextNode parent) {
		super(parent);
		this.customConverters = new ArrayList<EclipseLinkOrmCustomConverter>();
		this.objectTypeConverters = new ArrayList<EclipseLinkOrmObjectTypeConverter>();
		this.structConverters = new ArrayList<EclipseLinkOrmStructConverter>();
		this.typeConverters = new ArrayList<EclipseLinkOrmTypeConverter>();
	}

	public ListIterator<EclipseLinkOrmCustomConverter> customConverters() {
		return this.customConverters.listIterator();
	}

	public int customConvertersSize() {
		return this.customConverters.size();
	}

	public CustomConverter addCustomConverter(int index) {
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverterImpl();
		EclipseLinkOrmCustomConverter contextConverter = this.buildCustomConverter(resourceConverter);
		this.customConverters.add(index, contextConverter);
		this.resourceConvertersHolder.getConverters().add(index, resourceConverter);
		this.fireItemAdded(CUSTOM_CONVERTERS_LIST, index, contextConverter);
		return contextConverter;
	}

	protected void addCustomConverter(int index, EclipseLinkOrmCustomConverter converter) {
		addItemToList(index, converter, this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	protected void addCustomConverter(EclipseLinkOrmCustomConverter converter) {
		this.addCustomConverter(this.customConverters.size(), converter);
	}

	public void removeCustomConverter(int index) {
		EclipseLinkOrmCustomConverter removedConverter = this.customConverters.remove(index);
		this.resourceConvertersHolder.getConverters().remove(index);
		fireItemRemoved(CUSTOM_CONVERTERS_LIST, index, removedConverter);
	}

	public void removeCustomConverter(CustomConverter converter) {
		this.removeCustomConverter(this.customConverters.indexOf(converter));
	}

	protected void removeConverter_(CustomConverter converter) {
		removeItemFromList(converter, this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.customConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getConverters().move(targetIndex, sourceIndex);
		fireItemMoved(CUSTOM_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveCustomConverter_(int index, EclipseLinkOrmCustomConverter converter) {
		moveItemInList(index, this.customConverters.indexOf(converter), this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	public ListIterator<EclipseLinkOrmObjectTypeConverter> objectTypeConverters() {
		return this.objectTypeConverters.listIterator();
	}

	public int objectTypeConvertersSize() {
		return this.objectTypeConverters.size();
	}

	public ObjectTypeConverter addObjectTypeConverter(int index) {
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverterImpl();
		EclipseLinkOrmObjectTypeConverter contextObjectTypeConverter = this.buildObjectTypeConverter(resourceObjectTypeConverter);
		this.objectTypeConverters.add(index, contextObjectTypeConverter);
		this.resourceConvertersHolder.getObjectTypeConverters().add(index, resourceObjectTypeConverter);
		this.fireItemAdded(OBJECT_TYPE_CONVERTERS_LIST, index, contextObjectTypeConverter);
		return contextObjectTypeConverter;
	}

	protected void addObjectTypeConverter(int index, EclipseLinkOrmObjectTypeConverter converter) {
		addItemToList(index, converter, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	protected void addObjectTypeConverter(EclipseLinkOrmObjectTypeConverter converter) {
		this.addObjectTypeConverter(this.objectTypeConverters.size(), converter);
	}

	public void removeObjectTypeConverter(int index) {
		EclipseLinkOrmObjectTypeConverter removedObjectTypeConverter = this.objectTypeConverters.remove(index);
		this.resourceConvertersHolder.getObjectTypeConverters().remove(index);
		fireItemRemoved(OBJECT_TYPE_CONVERTERS_LIST, index, removedObjectTypeConverter);
	}

	public void removeObjectTypeConverter(ObjectTypeConverter converter) {
		this.removeObjectTypeConverter(this.objectTypeConverters.indexOf(converter));
	}

	protected void removeObjectTypeConverter_(ObjectTypeConverter converter) {
		removeItemFromList(converter, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.objectTypeConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getObjectTypeConverters().move(targetIndex, sourceIndex);
		fireItemMoved(OBJECT_TYPE_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveObjectTypeConverter_(int index, EclipseLinkOrmObjectTypeConverter converter) {
		moveItemInList(index, this.objectTypeConverters.indexOf(converter), this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	public ListIterator<EclipseLinkOrmStructConverter> structConverters() {
		return this.structConverters.listIterator();
	}

	public int structConvertersSize() {
		return this.structConverters.size();
	}

	public StructConverter addStructConverter(int index) {
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverterImpl();
		EclipseLinkOrmStructConverter contextStructConverter = this.buildStructConverter(resourceStructConverter);
		this.structConverters.add(index, contextStructConverter);
		this.resourceConvertersHolder.getStructConverters().add(index, resourceStructConverter);
		this.fireItemAdded(STRUCT_CONVERTERS_LIST, index, contextStructConverter);
		return contextStructConverter;
	}

	protected void addStructConverter(int index, EclipseLinkOrmStructConverter converter) {
		addItemToList(index, converter, this.structConverters, STRUCT_CONVERTERS_LIST);
	}

	protected void addStructConverter(EclipseLinkOrmStructConverter converter) {
		this.addStructConverter(this.structConverters.size(), converter);
	}

	public void removeStructConverter(int index) {
		EclipseLinkOrmStructConverter removedStructConverter = this.structConverters.remove(index);
		this.resourceConvertersHolder.getStructConverters().remove(index);
		fireItemRemoved(STRUCT_CONVERTERS_LIST, index, removedStructConverter);
	}

	public void removeStructConverter(StructConverter converter) {
		this.removeStructConverter(this.structConverters.indexOf(converter));
	}

	protected void removeStructConverter_(StructConverter converter) {
		removeItemFromList(converter, this.structConverters, STRUCT_CONVERTERS_LIST);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.structConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getStructConverters().move(targetIndex, sourceIndex);
		fireItemMoved(STRUCT_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveStructConverter_(int index, EclipseLinkOrmStructConverter converter) {
		moveItemInList(index, this.structConverters.indexOf(converter), this.structConverters, STRUCT_CONVERTERS_LIST);
	}


	public ListIterator<EclipseLinkOrmTypeConverter> typeConverters() {
		return this.typeConverters.listIterator();
	}

	public int typeConvertersSize() {
		return this.typeConverters.size();
	}

	public TypeConverter addTypeConverter(int index) {
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverterImpl();
		EclipseLinkOrmTypeConverter contextTypeConverter = this.buildTypeConverter(resourceTypeConverter);
		this.typeConverters.add(index, contextTypeConverter);
		this.resourceConvertersHolder.getTypeConverters().add(index, resourceTypeConverter);
		this.fireItemAdded(TYPE_CONVERTERS_LIST, index, contextTypeConverter);
		return contextTypeConverter;
	}

	protected void addTypeConverter(int index, EclipseLinkOrmTypeConverter converter) {
		addItemToList(index, converter, this.typeConverters, TYPE_CONVERTERS_LIST);
	}

	protected void addTypeConverter(EclipseLinkOrmTypeConverter converter) {
		this.addTypeConverter(this.typeConverters.size(), converter);
	}

	public void removeTypeConverter(int index) {
		EclipseLinkOrmTypeConverter removedTypeConverter = this.typeConverters.remove(index);
		this.resourceConvertersHolder.getTypeConverters().remove(index);
		fireItemRemoved(TYPE_CONVERTERS_LIST, index, removedTypeConverter);
	}

	public void removeTypeConverter(TypeConverter converter) {
		this.removeTypeConverter(this.typeConverters.indexOf(converter));
	}

	protected void removeTypeConverter_(TypeConverter converter) {
		removeItemFromList(converter, this.typeConverters, TYPE_CONVERTERS_LIST);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.typeConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getTypeConverters().move(targetIndex, sourceIndex);
		fireItemMoved(TYPE_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveTypeConverter_(int index, EclipseLinkOrmTypeConverter converter) {
		moveItemInList(index, this.typeConverters.indexOf(converter), this.typeConverters, TYPE_CONVERTERS_LIST);
	}
	

	
	public void initialize(XmlConvertersHolder resourceConvertersHolder) {
		this.resourceConvertersHolder = resourceConvertersHolder;
		this.initializeCustomConverters();
		this.initializeObjectTypeConverters();
		this.initializeStructConverters();
		this.initializeTypeConverters();		
	}
	
	protected void initializeCustomConverters() {
		for (XmlConverter resourceConverter : this.resourceConvertersHolder.getConverters()) {
			this.customConverters.add(this.buildCustomConverter(resourceConverter));
		}
	}
	
	protected void initializeObjectTypeConverters() {
		for (XmlObjectTypeConverter resourceConverter : this.resourceConvertersHolder.getObjectTypeConverters()) {
			this.objectTypeConverters.add(this.buildObjectTypeConverter(resourceConverter));
		}
	}
	
	protected void initializeStructConverters() {
		for (XmlStructConverter resourceConverter : this.resourceConvertersHolder.getStructConverters()) {
			this.structConverters.add(this.buildStructConverter(resourceConverter));
		}
	}
	
	protected void initializeTypeConverters() {
		for (XmlTypeConverter resourceConverter : this.resourceConvertersHolder.getTypeConverters()) {
			this.typeConverters.add(this.buildTypeConverter(resourceConverter));
		}
	}
	
	protected EclipseLinkOrmCustomConverter buildCustomConverter(XmlConverter resourceConverter) {
		EclipseLinkOrmCustomConverter contextConverter = new EclipseLinkOrmCustomConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected EclipseLinkOrmTypeConverter buildTypeConverter(XmlTypeConverter resourceConverter) {
		EclipseLinkOrmTypeConverter contextConverter = new EclipseLinkOrmTypeConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected EclipseLinkOrmObjectTypeConverter buildObjectTypeConverter(XmlObjectTypeConverter resourceConverter) {
		EclipseLinkOrmObjectTypeConverter contextConverter = new EclipseLinkOrmObjectTypeConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected EclipseLinkOrmStructConverter buildStructConverter(XmlStructConverter resourceConverter) {
		EclipseLinkOrmStructConverter contextConverter = new EclipseLinkOrmStructConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	public void update() {
		this.updateCustomConverters();
		this.updateObjectTypeConverters();
		this.updateStructConverters();
		this.updateTypeConverters();		
	}
	
	protected void updateCustomConverters() {
		Collection<EclipseLinkOrmCustomConverter> contextConvertersToRemove = CollectionTools.collection(customConverters());
		Collection<EclipseLinkOrmCustomConverter> contextConvertersToUpdate = new ArrayList<EclipseLinkOrmCustomConverter>();
		ListIterator<XmlConverter> resourceConverters = new CloneListIterator<XmlConverter>(this.resourceConvertersHolder.getConverters());//prevent ConcurrentModificiationException
		int resourceIndex = 0;
		
		for (XmlConverter resourceConverter : CollectionTools.iterable(resourceConverters)) {
			boolean contextConverterFound = false;
			for (EclipseLinkOrmCustomConverter contextConverter : contextConvertersToRemove) {
				if (contextConverter.getXmlResource() == resourceConverter) {
					moveCustomConverter_(resourceIndex, contextConverter);
					contextConvertersToRemove.remove(contextConverter);
					contextConvertersToUpdate.add(contextConverter);
					contextConverterFound = true;
					break;
				}
			}
			if (!contextConverterFound) {
				addCustomConverter(this.buildCustomConverter(resourceConverter));
			}
			resourceIndex++;
		}
		for (EclipseLinkOrmCustomConverter contextConverter : contextConvertersToRemove) {
			removeConverter_(contextConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (EclipseLinkOrmCustomConverter contextConverter : contextConvertersToUpdate) {
			contextConverter.update();
		}	
	}
	
	protected void updateObjectTypeConverters() {
		Collection<EclipseLinkOrmObjectTypeConverter> contextObjectTypeConvertersToRemove = CollectionTools.collection(objectTypeConverters());
		Collection<EclipseLinkOrmObjectTypeConverter> contextObjectTypeConvertersToUpdate = new ArrayList<EclipseLinkOrmObjectTypeConverter>();
		ListIterator<XmlObjectTypeConverter> resourceObjectTypeConverters = new CloneListIterator<XmlObjectTypeConverter>(this.resourceConvertersHolder.getObjectTypeConverters());//prevent ConcurrentModificiationException
		int resourceIndex = 0;
		
		for (XmlObjectTypeConverter resourceObjectTypeConverter : CollectionTools.iterable(resourceObjectTypeConverters)) {
			boolean contextObjectTypeConverterFound = false;
			for (EclipseLinkOrmObjectTypeConverter contextObjectTypeConverter : contextObjectTypeConvertersToRemove) {
				if (contextObjectTypeConverter.getXmlResource() == resourceObjectTypeConverter) {
					moveObjectTypeConverter_(resourceIndex, contextObjectTypeConverter);
					contextObjectTypeConvertersToRemove.remove(contextObjectTypeConverter);
					contextObjectTypeConvertersToUpdate.add(contextObjectTypeConverter);
					contextObjectTypeConverterFound = true;
					break;
				}
			}
			if (!contextObjectTypeConverterFound) {
				addObjectTypeConverter(this.buildObjectTypeConverter(resourceObjectTypeConverter));
			}
			resourceIndex++;
		}
		for (EclipseLinkOrmObjectTypeConverter contextObjectTypeConverter : contextObjectTypeConvertersToRemove) {
			removeObjectTypeConverter_(contextObjectTypeConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (EclipseLinkOrmObjectTypeConverter contextObjectTypeConverter : contextObjectTypeConvertersToUpdate) {
			contextObjectTypeConverter.update();
		}	
	}
	
	protected void updateTypeConverters() {
		Collection<EclipseLinkOrmTypeConverter> contextTypeConvertersToRemove = CollectionTools.collection(typeConverters());
		Collection<EclipseLinkOrmTypeConverter> contextTypeConvertersToUpdate = new ArrayList<EclipseLinkOrmTypeConverter>();
		ListIterator<XmlTypeConverter> resourceTypeConverters = new CloneListIterator<XmlTypeConverter>(this.resourceConvertersHolder.getTypeConverters());//prevent ConcurrentModificiationException
		int resourceIndex = 0;
		
		for (XmlTypeConverter resourceTypeConverter : CollectionTools.iterable(resourceTypeConverters)) {
			boolean contextTypeConverterFound = false;
			for (EclipseLinkOrmTypeConverter contextTypeConverter : contextTypeConvertersToRemove) {
				if (contextTypeConverter.getXmlResource() == resourceTypeConverter) {
					moveTypeConverter_(resourceIndex, contextTypeConverter);
					contextTypeConvertersToRemove.remove(contextTypeConverter);
					contextTypeConvertersToUpdate.add(contextTypeConverter);
					contextTypeConverterFound = true;
					break;
				}
			}
			if (!contextTypeConverterFound) {
				addTypeConverter(this.buildTypeConverter(resourceTypeConverter));
			}
			resourceIndex++;
		}
		for (EclipseLinkOrmTypeConverter contextTypeConverter : contextTypeConvertersToRemove) {
			removeTypeConverter_(contextTypeConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (EclipseLinkOrmTypeConverter contextTypeConverter : contextTypeConvertersToUpdate) {
			contextTypeConverter.update();
		}	
	}
	
	protected void updateStructConverters() {
		Collection<EclipseLinkOrmStructConverter> contextStructConvertersToRemove = CollectionTools.collection(structConverters());
		Collection<EclipseLinkOrmStructConverter> contextStructConvertersToUpdate = new ArrayList<EclipseLinkOrmStructConverter>();
		ListIterator<XmlStructConverter> resourceStructConverters = new CloneListIterator<XmlStructConverter>(this.resourceConvertersHolder.getStructConverters());//prevent ConcurrentModificiationException
		int resourceIndex = 0;
		
		for (XmlStructConverter resourceStructConverter : CollectionTools.iterable(resourceStructConverters)) {
			boolean contextStructConverterFound = false;
			for (EclipseLinkOrmStructConverter contextStructConverter : contextStructConvertersToRemove) {
				if (contextStructConverter.getXmlResource() == resourceStructConverter) {
					moveStructConverter_(resourceIndex, contextStructConverter);
					contextStructConvertersToRemove.remove(contextStructConverter);
					contextStructConvertersToUpdate.add(contextStructConverter);
					contextStructConverterFound = true;
					break;
				}
			}
			if (!contextStructConverterFound) {
				addStructConverter(this.buildStructConverter(resourceStructConverter));
			}
			resourceIndex++;
		}
		for (EclipseLinkOrmStructConverter contextStructConverter : contextStructConvertersToRemove) {
			removeStructConverter_(contextStructConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (EclipseLinkOrmStructConverter contextStructConverter : contextStructConvertersToUpdate) {
			contextStructConverter.update();
		}	
	}

	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
	}


	public TextRange getValidationTextRange() {
		return this.resourceConvertersHolder.getValidationTextRange();
	}


}

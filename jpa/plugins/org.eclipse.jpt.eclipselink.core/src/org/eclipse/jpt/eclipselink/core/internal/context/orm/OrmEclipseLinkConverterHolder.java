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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkConverterHolder extends AbstractOrmXmlContextNode implements EclipseLinkConverterHolder
{	
	private final XmlConvertersHolder resourceConvertersHolder;
	
	protected final List<OrmEclipseLinkCustomConverter> customConverters;
	protected final List<OrmEclipseLinkObjectTypeConverter> objectTypeConverters;
	protected final List<OrmEclipseLinkStructConverter> structConverters;
	protected final List<OrmEclipseLinkTypeConverter> typeConverters;
	
	public OrmEclipseLinkConverterHolder(XmlContextNode parent, XmlConvertersHolder resourceConvertersHolder) {
		super(parent);
		this.resourceConvertersHolder = resourceConvertersHolder;
		this.customConverters = new ArrayList<OrmEclipseLinkCustomConverter>();
		this.objectTypeConverters = new ArrayList<OrmEclipseLinkObjectTypeConverter>();
		this.structConverters = new ArrayList<OrmEclipseLinkStructConverter>();
		this.typeConverters = new ArrayList<OrmEclipseLinkTypeConverter>();
		this.initializeCustomConverters();
		this.initializeObjectTypeConverters();
		this.initializeStructConverters();
		this.initializeTypeConverters();		
	}

	public ListIterator<OrmEclipseLinkCustomConverter> customConverters() {
		return this.customConverters.listIterator();
	}

	public int customConvertersSize() {
		return this.customConverters.size();
	}

	public EclipseLinkCustomConverter addCustomConverter(int index) {
		XmlConverter resourceConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		OrmEclipseLinkCustomConverter contextConverter = this.buildCustomConverter(resourceConverter);
		this.customConverters.add(index, contextConverter);
		this.resourceConvertersHolder.getConverters().add(index, resourceConverter);
		this.fireItemAdded(CUSTOM_CONVERTERS_LIST, index, contextConverter);
		return contextConverter;
	}

	protected void addCustomConverter(int index, OrmEclipseLinkCustomConverter converter) {
		addItemToList(index, converter, this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	protected void addCustomConverter(OrmEclipseLinkCustomConverter converter) {
		this.addCustomConverter(this.customConverters.size(), converter);
	}

	public void removeCustomConverter(int index) {
		OrmEclipseLinkCustomConverter removedConverter = this.customConverters.remove(index);
		this.resourceConvertersHolder.getConverters().remove(index);
		fireItemRemoved(CUSTOM_CONVERTERS_LIST, index, removedConverter);
	}

	public void removeCustomConverter(EclipseLinkCustomConverter converter) {
		this.removeCustomConverter(this.customConverters.indexOf(converter));
	}

	protected void removeConverter_(EclipseLinkCustomConverter converter) {
		removeItemFromList(converter, this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.customConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getConverters().move(targetIndex, sourceIndex);
		fireItemMoved(CUSTOM_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveCustomConverter_(int index, OrmEclipseLinkCustomConverter converter) {
		moveItemInList(index, this.customConverters.indexOf(converter), this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	public ListIterator<OrmEclipseLinkObjectTypeConverter> objectTypeConverters() {
		return this.objectTypeConverters.listIterator();
	}

	public int objectTypeConvertersSize() {
		return this.objectTypeConverters.size();
	}

	public EclipseLinkObjectTypeConverter addObjectTypeConverter(int index) {
		XmlObjectTypeConverter resourceObjectTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		OrmEclipseLinkObjectTypeConverter contextObjectTypeConverter = this.buildObjectTypeConverter(resourceObjectTypeConverter);
		this.objectTypeConverters.add(index, contextObjectTypeConverter);
		this.resourceConvertersHolder.getObjectTypeConverters().add(index, resourceObjectTypeConverter);
		this.fireItemAdded(OBJECT_TYPE_CONVERTERS_LIST, index, contextObjectTypeConverter);
		return contextObjectTypeConverter;
	}

	protected void addObjectTypeConverter(int index, OrmEclipseLinkObjectTypeConverter converter) {
		addItemToList(index, converter, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	protected void addObjectTypeConverter(OrmEclipseLinkObjectTypeConverter converter) {
		this.addObjectTypeConverter(this.objectTypeConverters.size(), converter);
	}

	public void removeObjectTypeConverter(int index) {
		OrmEclipseLinkObjectTypeConverter removedObjectTypeConverter = this.objectTypeConverters.remove(index);
		this.resourceConvertersHolder.getObjectTypeConverters().remove(index);
		fireItemRemoved(OBJECT_TYPE_CONVERTERS_LIST, index, removedObjectTypeConverter);
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter converter) {
		this.removeObjectTypeConverter(this.objectTypeConverters.indexOf(converter));
	}

	protected void removeObjectTypeConverter_(EclipseLinkObjectTypeConverter converter) {
		removeItemFromList(converter, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.objectTypeConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getObjectTypeConverters().move(targetIndex, sourceIndex);
		fireItemMoved(OBJECT_TYPE_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveObjectTypeConverter_(int index, OrmEclipseLinkObjectTypeConverter converter) {
		moveItemInList(index, this.objectTypeConverters.indexOf(converter), this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	public ListIterator<OrmEclipseLinkStructConverter> structConverters() {
		return this.structConverters.listIterator();
	}

	public int structConvertersSize() {
		return this.structConverters.size();
	}

	public EclipseLinkStructConverter addStructConverter(int index) {
		XmlStructConverter resourceStructConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		OrmEclipseLinkStructConverter contextStructConverter = this.buildStructConverter(resourceStructConverter);
		this.structConverters.add(index, contextStructConverter);
		this.resourceConvertersHolder.getStructConverters().add(index, resourceStructConverter);
		this.fireItemAdded(STRUCT_CONVERTERS_LIST, index, contextStructConverter);
		return contextStructConverter;
	}

	protected void addStructConverter(int index, OrmEclipseLinkStructConverter converter) {
		addItemToList(index, converter, this.structConverters, STRUCT_CONVERTERS_LIST);
	}

	protected void addStructConverter(OrmEclipseLinkStructConverter converter) {
		this.addStructConverter(this.structConverters.size(), converter);
	}

	public void removeStructConverter(int index) {
		OrmEclipseLinkStructConverter removedStructConverter = this.structConverters.remove(index);
		this.resourceConvertersHolder.getStructConverters().remove(index);
		fireItemRemoved(STRUCT_CONVERTERS_LIST, index, removedStructConverter);
	}

	public void removeStructConverter(EclipseLinkStructConverter converter) {
		this.removeStructConverter(this.structConverters.indexOf(converter));
	}

	protected void removeStructConverter_(EclipseLinkStructConverter converter) {
		removeItemFromList(converter, this.structConverters, STRUCT_CONVERTERS_LIST);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.structConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getStructConverters().move(targetIndex, sourceIndex);
		fireItemMoved(STRUCT_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveStructConverter_(int index, OrmEclipseLinkStructConverter converter) {
		moveItemInList(index, this.structConverters.indexOf(converter), this.structConverters, STRUCT_CONVERTERS_LIST);
	}


	public ListIterator<OrmEclipseLinkTypeConverter> typeConverters() {
		return this.typeConverters.listIterator();
	}

	public int typeConvertersSize() {
		return this.typeConverters.size();
	}

	public EclipseLinkTypeConverter addTypeConverter(int index) {
		XmlTypeConverter resourceTypeConverter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		OrmEclipseLinkTypeConverter contextTypeConverter = this.buildTypeConverter(resourceTypeConverter);
		this.typeConverters.add(index, contextTypeConverter);
		this.resourceConvertersHolder.getTypeConverters().add(index, resourceTypeConverter);
		this.fireItemAdded(TYPE_CONVERTERS_LIST, index, contextTypeConverter);
		return contextTypeConverter;
	}

	protected void addTypeConverter(int index, OrmEclipseLinkTypeConverter converter) {
		addItemToList(index, converter, this.typeConverters, TYPE_CONVERTERS_LIST);
	}

	protected void addTypeConverter(OrmEclipseLinkTypeConverter converter) {
		this.addTypeConverter(this.typeConverters.size(), converter);
	}

	public void removeTypeConverter(int index) {
		OrmEclipseLinkTypeConverter removedTypeConverter = this.typeConverters.remove(index);
		this.resourceConvertersHolder.getTypeConverters().remove(index);
		fireItemRemoved(TYPE_CONVERTERS_LIST, index, removedTypeConverter);
	}

	public void removeTypeConverter(EclipseLinkTypeConverter converter) {
		this.removeTypeConverter(this.typeConverters.indexOf(converter));
	}

	protected void removeTypeConverter_(EclipseLinkTypeConverter converter) {
		removeItemFromList(converter, this.typeConverters, TYPE_CONVERTERS_LIST);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.typeConverters, targetIndex, sourceIndex);
		this.resourceConvertersHolder.getTypeConverters().move(targetIndex, sourceIndex);
		fireItemMoved(TYPE_CONVERTERS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void moveTypeConverter_(int index, OrmEclipseLinkTypeConverter converter) {
		moveItemInList(index, this.typeConverters.indexOf(converter), this.typeConverters, TYPE_CONVERTERS_LIST);
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
	
	protected OrmEclipseLinkCustomConverter buildCustomConverter(XmlConverter resourceConverter) {
		OrmEclipseLinkCustomConverter contextConverter = new OrmEclipseLinkCustomConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected OrmEclipseLinkTypeConverter buildTypeConverter(XmlTypeConverter resourceConverter) {
		OrmEclipseLinkTypeConverter contextConverter = new OrmEclipseLinkTypeConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected OrmEclipseLinkObjectTypeConverter buildObjectTypeConverter(XmlObjectTypeConverter resourceConverter) {
		OrmEclipseLinkObjectTypeConverter contextConverter = new OrmEclipseLinkObjectTypeConverter(this);
		contextConverter.initialize(resourceConverter);
		return contextConverter;
	}

	protected OrmEclipseLinkStructConverter buildStructConverter(XmlStructConverter resourceConverter) {
		OrmEclipseLinkStructConverter contextConverter = new OrmEclipseLinkStructConverter(this);
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
		Collection<OrmEclipseLinkCustomConverter> contextConvertersToRemove = CollectionTools.collection(customConverters());
		Collection<OrmEclipseLinkCustomConverter> contextConvertersToUpdate = new ArrayList<OrmEclipseLinkCustomConverter>();
		int resourceIndex = 0;
		
		// make a copy of the XML conversion values (to prevent ConcurrentModificationException)
		List<XmlConverter> xmlConverters = this.resourceConvertersHolder.getConverters();
		for (XmlConverter resourceConverter : xmlConverters.toArray(new XmlConverter[xmlConverters.size()])) {
			boolean contextConverterFound = false;
			for (OrmEclipseLinkCustomConverter contextConverter : contextConvertersToRemove) {
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
		for (OrmEclipseLinkCustomConverter contextConverter : contextConvertersToRemove) {
			removeConverter_(contextConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (OrmEclipseLinkCustomConverter contextConverter : contextConvertersToUpdate) {
			contextConverter.update();
		}	
	}
	
	protected void updateObjectTypeConverters() {
		Collection<OrmEclipseLinkObjectTypeConverter> contextConvertersToRemove = CollectionTools.collection(objectTypeConverters());
		Collection<OrmEclipseLinkObjectTypeConverter> contextConvertersToUpdate = new ArrayList<OrmEclipseLinkObjectTypeConverter>();
		int resourceIndex = 0;

		// make a copy of the XML converters (to prevent ConcurrentModificationException)
		List<XmlObjectTypeConverter> xmlConverters = this.resourceConvertersHolder.getObjectTypeConverters();
		for (XmlObjectTypeConverter xmlConverter : xmlConverters.toArray(new XmlObjectTypeConverter[xmlConverters.size()])) {
			boolean contextConverterFound = false;
			for (OrmEclipseLinkObjectTypeConverter contextObjectTypeConverter : contextConvertersToRemove) {
				if (contextObjectTypeConverter.getXmlResource() == xmlConverter) {
					moveObjectTypeConverter_(resourceIndex, contextObjectTypeConverter);
					contextConvertersToRemove.remove(contextObjectTypeConverter);
					contextConvertersToUpdate.add(contextObjectTypeConverter);
					contextConverterFound = true;
					break;
				}
			}
			if (!contextConverterFound) {
				addObjectTypeConverter(this.buildObjectTypeConverter(xmlConverter));
			}
			resourceIndex++;
		}
		for (OrmEclipseLinkObjectTypeConverter contextObjectTypeConverter : contextConvertersToRemove) {
			removeObjectTypeConverter_(contextObjectTypeConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (OrmEclipseLinkObjectTypeConverter contextObjectTypeConverter : contextConvertersToUpdate) {
			contextObjectTypeConverter.update();
		}	
	}
	
	protected void updateTypeConverters() {
		Collection<OrmEclipseLinkTypeConverter> contextConvertersToRemove = CollectionTools.collection(typeConverters());
		Collection<OrmEclipseLinkTypeConverter> contextConvertersToUpdate = new ArrayList<OrmEclipseLinkTypeConverter>();
		int resourceIndex = 0;
		
		List<XmlTypeConverter> xmlConverters = this.resourceConvertersHolder.getTypeConverters();
		for (XmlTypeConverter xmlConverter : xmlConverters.toArray(new XmlTypeConverter[xmlConverters.size()])) {
			boolean contextTypeConverterFound = false;
			for (OrmEclipseLinkTypeConverter contextTypeConverter : contextConvertersToRemove) {
				if (contextTypeConverter.getXmlResource() == xmlConverter) {
					moveTypeConverter_(resourceIndex, contextTypeConverter);
					contextConvertersToRemove.remove(contextTypeConverter);
					contextConvertersToUpdate.add(contextTypeConverter);
					contextTypeConverterFound = true;
					break;
				}
			}
			if (!contextTypeConverterFound) {
				addTypeConverter(this.buildTypeConverter(xmlConverter));
			}
			resourceIndex++;
		}
		for (OrmEclipseLinkTypeConverter contextTypeConverter : contextConvertersToRemove) {
			removeTypeConverter_(contextTypeConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (OrmEclipseLinkTypeConverter contextTypeConverter : contextConvertersToUpdate) {
			contextTypeConverter.update();
		}	
	}
	
	protected void updateStructConverters() {
		Collection<OrmEclipseLinkStructConverter> contextConvertersToRemove = CollectionTools.collection(structConverters());
		Collection<OrmEclipseLinkStructConverter> contextConvertersToUpdate = new ArrayList<OrmEclipseLinkStructConverter>();
		int resourceIndex = 0;
		
		// make a copy of the XML converters (to prevent ConcurrentModificationException)
		List<XmlStructConverter> xmlConverters = this.resourceConvertersHolder.getStructConverters();
		for (XmlStructConverter resourceStructConverter : xmlConverters.toArray(new XmlStructConverter[xmlConverters.size()])) {
			boolean contextStructConverterFound = false;
			for (OrmEclipseLinkStructConverter contextStructConverter : contextConvertersToRemove) {
				if (contextStructConverter.getXmlResource() == resourceStructConverter) {
					moveStructConverter_(resourceIndex, contextStructConverter);
					contextConvertersToRemove.remove(contextStructConverter);
					contextConvertersToUpdate.add(contextStructConverter);
					contextStructConverterFound = true;
					break;
				}
			}
			if (!contextStructConverterFound) {
				addStructConverter(this.buildStructConverter(resourceStructConverter));
			}
			resourceIndex++;
		}
		for (OrmEclipseLinkStructConverter contextStructConverter : contextConvertersToRemove) {
			removeStructConverter_(contextStructConverter);
		}
		//first handle adding/removing of the converters, then update the others last, 
		//this causes less churn in the update process
		for (OrmEclipseLinkStructConverter contextStructConverter : contextConvertersToUpdate) {
			contextStructConverter.update();
		}	
	}

	//************ validation ***************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
	}


	public TextRange getValidationTextRange() {
		return this.resourceConvertersHolder.getValidationTextRange();
	}


}

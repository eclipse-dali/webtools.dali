/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.OrmEclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkConverterContainerImpl
	extends AbstractOrmXmlContextNode
	implements OrmEclipseLinkConverterContainer
{
	private final XmlConvertersHolder xmlConvertersHolder;

	protected final Vector<OrmEclipseLinkCustomConverter> customConverters = new Vector<OrmEclipseLinkCustomConverter>();
	protected final CustomConverterContainerAdapter customConverterContainerAdapter;

	protected final Vector<OrmEclipseLinkObjectTypeConverter> objectTypeConverters = new Vector<OrmEclipseLinkObjectTypeConverter>();
	protected final ObjectTypeConverterContainerAdapter objectTypeConverterContainerAdapter;

	protected final Vector<OrmEclipseLinkStructConverter> structConverters = new Vector<OrmEclipseLinkStructConverter>();
	protected final StructConverterContainerAdapter structConverterContainerAdapter;

	protected final Vector<OrmEclipseLinkTypeConverter> typeConverters = new Vector<OrmEclipseLinkTypeConverter>();
	protected final TypeConverterContainerAdapter typeConverterContainerAdapter;


	public OrmEclipseLinkConverterContainerImpl(XmlContextNode parent, XmlConvertersHolder xmlConvertersHolder) {
		super(parent);
		this.xmlConvertersHolder = xmlConvertersHolder;

		this.customConverterContainerAdapter = this.buildCustomConverterContainerAdapter();
		this.objectTypeConverterContainerAdapter = this.buildObjectTypeConverterContainerAdapter();
		this.structConverterContainerAdapter = this.buildStructConverterContainerAdapter();
		this.typeConverterContainerAdapter = this.buildTypeConverterContainerAdapter();

		this.initializeCustomConverters();
		this.initializeObjectTypeConverters();
		this.initializeStructConverters();
		this.initializeTypeConverters();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.syncCustomConverters();
		this.syncObjectTypeConverters();
		this.syncStructConverters();
		this.syncTypeConverters();
	}

	@Override
	public void update() {
		super.update();
		this.updateNodes(this.getCustomConverters());
		this.updateNodes(this.getObjectTypeConverters());
		this.updateNodes(this.getStructConverters());
		this.updateNodes(this.getTypeConverters());
	}


	// ********** custom converters **********

	@SuppressWarnings("unchecked")
	public ListIterator<OrmEclipseLinkCustomConverter> customConverters() {
		return this.getCustomConverters().iterator();
	}

	public ListIterable<OrmEclipseLinkCustomConverter> getCustomConverters() {
		return new LiveCloneListIterable<OrmEclipseLinkCustomConverter>(this.customConverters);
	}

	public int customConvertersSize() {
		return this.customConverters.size();
	}

	public OrmEclipseLinkCustomConverter addCustomConverter() {
		return this.addCustomConverter(this.customConverters.size());
	}

	public OrmEclipseLinkCustomConverter addCustomConverter(int index) {
		XmlConverter xmlConverter = this.buildXmlCustomConverter();
		OrmEclipseLinkCustomConverter converter = this.addCustomConverter_(index, xmlConverter);
		this.xmlConvertersHolder.getConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlConverter buildXmlCustomConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
	}

	public void removeCustomConverter(EclipseLinkCustomConverter converter) {
		this.removeCustomConverter(this.customConverters.indexOf(converter));
	}

	public void removeCustomConverter(int index) {
		this.removeCustomConverter_(index);
		this.xmlConvertersHolder.getConverters().remove(index);
	}

	protected void removeCustomConverter_(int index) {
		this.removeItemFromList(index, this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.customConverters, CUSTOM_CONVERTERS_LIST);
		this.xmlConvertersHolder.getConverters().move(targetIndex, sourceIndex);
	}

	protected void initializeCustomConverters() {
		for (XmlConverter xmlConverter : this.getXmlCustomConverters()) {
			this.customConverters.add(this.buildCustomConverter(xmlConverter));
		}
	}

	protected OrmEclipseLinkCustomConverter buildCustomConverter(XmlConverter xmlConverter) {
		return new OrmEclipseLinkCustomConverter(this, xmlConverter);
	}

	protected void syncCustomConverters() {
		ContextContainerTools.synchronizeWithResourceModel(this.customConverterContainerAdapter);
	}

	protected Iterable<XmlConverter> getXmlCustomConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlConverter>(this.xmlConvertersHolder.getConverters());
	}

	protected void moveCustomConverter_(int index, OrmEclipseLinkCustomConverter converter) {
		this.moveItemInList(index, converter, this.customConverters, CUSTOM_CONVERTERS_LIST);
	}

	protected OrmEclipseLinkCustomConverter addCustomConverter_(int index, XmlConverter xmlConverter) {
		OrmEclipseLinkCustomConverter converter = this.buildCustomConverter(xmlConverter);
		this.addItemToList(index, converter, this.customConverters, CUSTOM_CONVERTERS_LIST);
		return converter;
	}

	protected void removeCustomConverter_(OrmEclipseLinkCustomConverter converter) {
		this.removeCustomConverter_(this.customConverters.indexOf(converter));
	}

	protected CustomConverterContainerAdapter buildCustomConverterContainerAdapter() {
		return new CustomConverterContainerAdapter();
	}

	/**
	 * custom converter container adapter
	 */
	protected class CustomConverterContainerAdapter
		implements ContextContainerTools.Adapter<OrmEclipseLinkCustomConverter, XmlConverter>
	{
		public Iterable<OrmEclipseLinkCustomConverter> getContextElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getCustomConverters();
		}
		public Iterable<XmlConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlCustomConverters();
		}
		public XmlConverter getResourceElement(OrmEclipseLinkCustomConverter contextElement) {
			return contextElement.getXmlConverter();
		}
		public void moveContextElement(int index, OrmEclipseLinkCustomConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.moveCustomConverter_(index, element);
		}
		public void addContextElement(int index, XmlConverter resourceElement) {
			OrmEclipseLinkConverterContainerImpl.this.addCustomConverter_(index, resourceElement);
		}
		public void removeContextElement(OrmEclipseLinkCustomConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.removeCustomConverter_(element);
		}
	}


	// ********** object type converters **********

	@SuppressWarnings("unchecked")
	public ListIterator<OrmEclipseLinkObjectTypeConverter> objectTypeConverters() {
		return this.getObjectTypeConverters().iterator();
	}

	public ListIterable<OrmEclipseLinkObjectTypeConverter> getObjectTypeConverters() {
		return new LiveCloneListIterable<OrmEclipseLinkObjectTypeConverter>(this.objectTypeConverters);
	}

	public int objectTypeConvertersSize() {
		return this.objectTypeConverters.size();
	}

	public OrmEclipseLinkObjectTypeConverter addObjectTypeConverter() {
		return this.addObjectTypeConverter(this.objectTypeConverters.size());
	}

	public OrmEclipseLinkObjectTypeConverter addObjectTypeConverter(int index) {
		XmlObjectTypeConverter xmlConverter = this.buildXmlObjectTypeConverter();
		OrmEclipseLinkObjectTypeConverter converter = this.addObjectTypeConverter_(index, xmlConverter);
		this.xmlConvertersHolder.getObjectTypeConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlObjectTypeConverter buildXmlObjectTypeConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter converter) {
		this.removeObjectTypeConverter(this.objectTypeConverters.indexOf(converter));
	}

	public void removeObjectTypeConverter(int index) {
		this.removeObjectTypeConverter_(index);
		this.xmlConvertersHolder.getObjectTypeConverters().remove(index);
	}

	protected void removeObjectTypeConverter_(int index) {
		this.removeItemFromList(index, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
		this.xmlConvertersHolder.getObjectTypeConverters().move(targetIndex, sourceIndex);
	}

	protected void initializeObjectTypeConverters() {
		for (XmlObjectTypeConverter xmlConverter : this.getXmlObjectTypeConverters()) {
			this.objectTypeConverters.add(this.buildObjectTypeConverter(xmlConverter));
		}
	}

	protected OrmEclipseLinkObjectTypeConverter buildObjectTypeConverter(XmlObjectTypeConverter xmlConverter) {
		return new OrmEclipseLinkObjectTypeConverter(this, xmlConverter);
	}

	protected void syncObjectTypeConverters() {
		ContextContainerTools.synchronizeWithResourceModel(this.objectTypeConverterContainerAdapter);
	}

	protected Iterable<XmlObjectTypeConverter> getXmlObjectTypeConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlObjectTypeConverter>(this.xmlConvertersHolder.getObjectTypeConverters());
	}

	protected void moveObjectTypeConverter_(int index, OrmEclipseLinkObjectTypeConverter converter) {
		this.moveItemInList(index, converter, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
	}

	protected OrmEclipseLinkObjectTypeConverter addObjectTypeConverter_(int index, XmlObjectTypeConverter xmlConverter) {
		OrmEclipseLinkObjectTypeConverter converter = this.buildObjectTypeConverter(xmlConverter);
		this.addItemToList(index, converter, this.objectTypeConverters, OBJECT_TYPE_CONVERTERS_LIST);
		return converter;
	}

	protected void removeObjectTypeConverter_(OrmEclipseLinkObjectTypeConverter converter) {
		this.removeObjectTypeConverter_(this.objectTypeConverters.indexOf(converter));
	}

	protected ObjectTypeConverterContainerAdapter buildObjectTypeConverterContainerAdapter() {
		return new ObjectTypeConverterContainerAdapter();
	}

	/**
	 * object type converter container adapter
	 */
	protected class ObjectTypeConverterContainerAdapter
		implements ContextContainerTools.Adapter<OrmEclipseLinkObjectTypeConverter, XmlObjectTypeConverter>
	{
		public Iterable<OrmEclipseLinkObjectTypeConverter> getContextElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getObjectTypeConverters();
		}
		public Iterable<XmlObjectTypeConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlObjectTypeConverters();
		}
		public XmlObjectTypeConverter getResourceElement(OrmEclipseLinkObjectTypeConverter contextElement) {
			return contextElement.getXmlConverter();
		}
		public void moveContextElement(int index, OrmEclipseLinkObjectTypeConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.moveObjectTypeConverter_(index, element);
		}
		public void addContextElement(int index, XmlObjectTypeConverter resourceElement) {
			OrmEclipseLinkConverterContainerImpl.this.addObjectTypeConverter_(index, resourceElement);
		}
		public void removeContextElement(OrmEclipseLinkObjectTypeConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.removeObjectTypeConverter_(element);
		}
	}


	// ********** struct converters **********

	@SuppressWarnings("unchecked")
	public ListIterator<OrmEclipseLinkStructConverter> structConverters() {
		return this.getStructConverters().iterator();
	}

	public ListIterable<OrmEclipseLinkStructConverter> getStructConverters() {
		return new LiveCloneListIterable<OrmEclipseLinkStructConverter>(this.structConverters);
	}

	public int structConvertersSize() {
		return this.structConverters.size();
	}

	public OrmEclipseLinkStructConverter addStructConverter() {
		return this.addStructConverter(this.structConverters.size());
	}

	public OrmEclipseLinkStructConverter addStructConverter(int index) {
		XmlStructConverter xmlConverter = this.buildXmlStructConverter();
		OrmEclipseLinkStructConverter converter = this.addStructConverter_(index, xmlConverter);
		this.xmlConvertersHolder.getStructConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlStructConverter buildXmlStructConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
	}

	public void removeStructConverter(EclipseLinkStructConverter converter) {
		this.removeStructConverter(this.structConverters.indexOf(converter));
	}

	public void removeStructConverter(int index) {
		this.removeStructConverter_(index);
		this.xmlConvertersHolder.getStructConverters().remove(index);
	}

	protected void removeStructConverter_(int index) {
		this.removeItemFromList(index, this.structConverters, STRUCT_CONVERTERS_LIST);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.structConverters, STRUCT_CONVERTERS_LIST);
		this.xmlConvertersHolder.getStructConverters().move(targetIndex, sourceIndex);
	}

	protected void initializeStructConverters() {
		for (XmlStructConverter xmlConverter : this.getXmlStructConverters()) {
			this.structConverters.add(this.buildStructConverter(xmlConverter));
		}
	}

	protected OrmEclipseLinkStructConverter buildStructConverter(XmlStructConverter xmlConverter) {
		return new OrmEclipseLinkStructConverter(this, xmlConverter);
	}

	protected void syncStructConverters() {
		ContextContainerTools.synchronizeWithResourceModel(this.structConverterContainerAdapter);
	}

	protected Iterable<XmlStructConverter> getXmlStructConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlStructConverter>(this.xmlConvertersHolder.getStructConverters());
	}

	protected void moveStructConverter_(int index, OrmEclipseLinkStructConverter converter) {
		this.moveItemInList(index, converter, this.structConverters, STRUCT_CONVERTERS_LIST);
	}

	protected OrmEclipseLinkStructConverter addStructConverter_(int index, XmlStructConverter xmlConverter) {
		OrmEclipseLinkStructConverter converter = this.buildStructConverter(xmlConverter);
		this.addItemToList(index, converter, this.structConverters, STRUCT_CONVERTERS_LIST);
		return converter;
	}

	protected void removeStructConverter_(OrmEclipseLinkStructConverter converter) {
		this.removeStructConverter_(this.structConverters.indexOf(converter));
	}

	protected StructConverterContainerAdapter buildStructConverterContainerAdapter() {
		return new StructConverterContainerAdapter();
	}

	/**
	 * struct converter container adapter
	 */
	protected class StructConverterContainerAdapter
		implements ContextContainerTools.Adapter<OrmEclipseLinkStructConverter, XmlStructConverter>
	{
		public Iterable<OrmEclipseLinkStructConverter> getContextElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getStructConverters();
		}
		public Iterable<XmlStructConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlStructConverters();
		}
		public XmlStructConverter getResourceElement(OrmEclipseLinkStructConverter contextElement) {
			return contextElement.getXmlConverter();
		}
		public void moveContextElement(int index, OrmEclipseLinkStructConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.moveStructConverter_(index, element);
		}
		public void addContextElement(int index, XmlStructConverter resourceElement) {
			OrmEclipseLinkConverterContainerImpl.this.addStructConverter_(index, resourceElement);
		}
		public void removeContextElement(OrmEclipseLinkStructConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.removeStructConverter_(element);
		}
	}


	// ********** type converters **********

	@SuppressWarnings("unchecked")
	public ListIterator<OrmEclipseLinkTypeConverter> typeConverters() {
		return this.getTypeConverters().iterator();
	}

	public ListIterable<OrmEclipseLinkTypeConverter> getTypeConverters() {
		return new LiveCloneListIterable<OrmEclipseLinkTypeConverter>(this.typeConverters);
	}

	public int typeConvertersSize() {
		return this.typeConverters.size();
	}

	public OrmEclipseLinkTypeConverter addTypeConverter() {
		return this.addTypeConverter(this.typeConverters.size());
	}

	public OrmEclipseLinkTypeConverter addTypeConverter(int index) {
		XmlTypeConverter xmlConverter = this.buildXmlTypeConverter();
		OrmEclipseLinkTypeConverter converter = this.addTypeConverter_(index, xmlConverter);
		this.xmlConvertersHolder.getTypeConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlTypeConverter buildXmlTypeConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
	}

	public void removeTypeConverter(EclipseLinkTypeConverter converter) {
		this.removeTypeConverter(this.typeConverters.indexOf(converter));
	}

	public void removeTypeConverter(int index) {
		this.removeTypeConverter_(index);
		this.xmlConvertersHolder.getTypeConverters().remove(index);
	}

	protected void removeTypeConverter_(int index) {
		this.removeItemFromList(index, this.typeConverters, TYPE_CONVERTERS_LIST);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		this.moveItemInList(targetIndex, sourceIndex, this.typeConverters, TYPE_CONVERTERS_LIST);
		this.xmlConvertersHolder.getTypeConverters().move(targetIndex, sourceIndex);
	}

	protected void initializeTypeConverters() {
		for (XmlTypeConverter xmlConverter : this.getXmlTypeConverters()) {
			this.typeConverters.add(this.buildTypeConverter(xmlConverter));
		}
	}

	protected OrmEclipseLinkTypeConverter buildTypeConverter(XmlTypeConverter xmlConverter) {
		return new OrmEclipseLinkTypeConverter(this, xmlConverter);
	}

	protected void syncTypeConverters() {
		ContextContainerTools.synchronizeWithResourceModel(this.typeConverterContainerAdapter);
	}

	protected Iterable<XmlTypeConverter> getXmlTypeConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneIterable<XmlTypeConverter>(this.xmlConvertersHolder.getTypeConverters());
	}

	protected void moveTypeConverter_(int index, OrmEclipseLinkTypeConverter converter) {
		this.moveItemInList(index, converter, this.typeConverters, TYPE_CONVERTERS_LIST);
	}

	protected OrmEclipseLinkTypeConverter addTypeConverter_(int index, XmlTypeConverter xmlConverter) {
		OrmEclipseLinkTypeConverter converter = this.buildTypeConverter(xmlConverter);
		this.addItemToList(index, converter, this.typeConverters, TYPE_CONVERTERS_LIST);
		return converter;
	}

	protected void removeTypeConverter_(OrmEclipseLinkTypeConverter converter) {
		this.removeTypeConverter_(this.typeConverters.indexOf(converter));
	}

	protected TypeConverterContainerAdapter buildTypeConverterContainerAdapter() {
		return new TypeConverterContainerAdapter();
	}

	/**
	 * type converter container adapter
	 */
	protected class TypeConverterContainerAdapter
		implements ContextContainerTools.Adapter<OrmEclipseLinkTypeConverter, XmlTypeConverter>
	{
		public Iterable<OrmEclipseLinkTypeConverter> getContextElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getTypeConverters();
		}
		public Iterable<XmlTypeConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlTypeConverters();
		}
		public XmlTypeConverter getResourceElement(OrmEclipseLinkTypeConverter contextElement) {
			return contextElement.getXmlConverter();
		}
		public void moveContextElement(int index, OrmEclipseLinkTypeConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.moveTypeConverter_(index, element);
		}
		public void addContextElement(int index, XmlTypeConverter resourceElement) {
			OrmEclipseLinkConverterContainerImpl.this.addTypeConverter_(index, resourceElement);
		}
		public void removeContextElement(OrmEclipseLinkTypeConverter element) {
			OrmEclipseLinkConverterContainerImpl.this.removeTypeConverter_(element);
		}
	}


	// ********** refactoring **********

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			this.createRenameObjectTypeConverterEdits(originalType, newName),
			this.createRenameTypeConverterEdits(originalType, newName),
			this.createRenameStructConverterEdits(originalType, newName),
			this.createRenameCustomConverterEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypeConverterEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkObjectTypeConverter, Iterable<ReplaceEdit>>(getObjectTypeConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkObjectTypeConverter objectTypeConverter) {
					return objectTypeConverter.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createRenameTypeConverterEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkTypeConverter, Iterable<ReplaceEdit>>(getTypeConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkTypeConverter typeConverter) {
					return typeConverter.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createRenameStructConverterEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkStructConverter, Iterable<ReplaceEdit>>(getStructConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkStructConverter structConverter) {
					return structConverter.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createRenameCustomConverterEdits(final IType originalType, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkCustomConverter, Iterable<ReplaceEdit>>(getCustomConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkCustomConverter customConverter) {
					return customConverter.createRenameTypeEdits(originalType, newName);
				}
			}
		);
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			this.createMoveObjectTypeConverterEdits(originalType, newPackage),
			this.createMoveTypeConverterEdits(originalType, newPackage),
			this.createMoveStructConverterEdits(originalType, newPackage),
			this.createMoveCustomConverterEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMoveObjectTypeConverterEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkObjectTypeConverter, Iterable<ReplaceEdit>>(getObjectTypeConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkObjectTypeConverter objectTypeConverter) {
					return objectTypeConverter.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createMoveTypeConverterEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkTypeConverter, Iterable<ReplaceEdit>>(getTypeConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkTypeConverter typeConverter) {
					return typeConverter.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createMoveStructConverterEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkStructConverter, Iterable<ReplaceEdit>>(getStructConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkStructConverter structConverter) {
					return structConverter.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createMoveCustomConverterEdits(final IType originalType, final IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkCustomConverter, Iterable<ReplaceEdit>>(getCustomConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkCustomConverter customConverter) {
					return customConverter.createMoveTypeEdits(originalType, newPackage);
				}
			}
		);
	}


	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			this.createObjectTypeConverterRenamePackageEdits(originalPackage, newName),
			this.createTypeConverterRenamePackageEdits(originalPackage, newName),
			this.createStructConverterRenamePackageEdits(originalPackage, newName),
			this.createCustomConverterRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createObjectTypeConverterRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkObjectTypeConverter, Iterable<ReplaceEdit>>(getObjectTypeConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkObjectTypeConverter objectTypeConverter) {
					return objectTypeConverter.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createTypeConverterRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkTypeConverter, Iterable<ReplaceEdit>>(getTypeConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkTypeConverter typeConverter) {
					return typeConverter.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createStructConverterRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkStructConverter, Iterable<ReplaceEdit>>(getStructConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkStructConverter structConverter) {
					return structConverter.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}

	protected Iterable<ReplaceEdit> createCustomConverterRenamePackageEdits(final IPackageFragment originalPackage, final String newName) {
		return new CompositeIterable<ReplaceEdit>(
			new TransformationIterable<OrmEclipseLinkCustomConverter, Iterable<ReplaceEdit>>(getCustomConverters()) {
				@Override
				protected Iterable<ReplaceEdit> transform(OrmEclipseLinkCustomConverter customConverter) {
					return customConverter.createRenamePackageEdits(originalPackage, newName);
				}
			}
		);
	}


	// ********** validation **********

	/**
	 * The converters are validated in the persistence unit.
	 * @see org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit#validateConverters(List, IReporter)
	 */
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		// converters are validated in the persistence unit
	}
	
	public TextRange getValidationTextRange() {
		TextRange textRange = this.xmlConvertersHolder.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}


	// ********** misc **********

	@Override
	public XmlContextNode getParent() {
		return (XmlContextNode) super.getParent();
	}
}

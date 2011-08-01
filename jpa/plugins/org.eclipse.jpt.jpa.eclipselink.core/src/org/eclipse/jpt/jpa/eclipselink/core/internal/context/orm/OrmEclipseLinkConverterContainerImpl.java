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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;
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

	protected final ContextListContainer<OrmEclipseLinkCustomConverter, XmlConverter> customConverterContainer;
	protected final ContextListContainer<OrmEclipseLinkObjectTypeConverter, XmlObjectTypeConverter> objectTypeConverterContainer;
	protected final ContextListContainer<OrmEclipseLinkStructConverter, XmlStructConverter> structConverterContainer;
	protected final ContextListContainer<OrmEclipseLinkTypeConverter, XmlTypeConverter> typeConverterContainer;


	public OrmEclipseLinkConverterContainerImpl(XmlContextNode parent, XmlConvertersHolder xmlConvertersHolder) {
		super(parent);
		this.xmlConvertersHolder = xmlConvertersHolder;

		this.customConverterContainer = this.buildCustomConverterContainer();
		this.objectTypeConverterContainer = this.buildObjectTypeConverterContainer();
		this.structConverterContainer = this.buildStructConverterContainer();
		this.typeConverterContainer = this.buildTypeConverterContainer();
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

	public ListIterable<OrmEclipseLinkCustomConverter> getCustomConverters() {
		return this.customConverterContainer.getContextElements();
	}

	public int getCustomConvertersSize() {
		return this.customConverterContainer.getContextElementsSize();
	}

	public OrmEclipseLinkCustomConverter addCustomConverter() {
		return this.addCustomConverter(this.getCustomConvertersSize());
	}

	public OrmEclipseLinkCustomConverter addCustomConverter(int index) {
		XmlConverter xmlConverter = this.buildXmlCustomConverter();
		OrmEclipseLinkCustomConverter converter = this.customConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConvertersHolder.getConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlConverter buildXmlCustomConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
	}

	public void removeCustomConverter(EclipseLinkCustomConverter converter) {
		this.removeCustomConverter(this.customConverterContainer.indexOfContextElement((OrmEclipseLinkCustomConverter) converter));
	}

	public void removeCustomConverter(int index) {
		this.customConverterContainer.removeContextElement(index);
		this.xmlConvertersHolder.getConverters().remove(index);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		this.customConverterContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlConvertersHolder.getConverters().move(targetIndex, sourceIndex);
	}

	protected OrmEclipseLinkCustomConverter buildCustomConverter(XmlConverter xmlConverter) {
		return new OrmEclipseLinkCustomConverter(this, xmlConverter);
	}

	protected void syncCustomConverters() {
		this.customConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlConverter> getXmlCustomConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlConverter>(this.xmlConvertersHolder.getConverters());
	}

	protected ContextListContainer<OrmEclipseLinkCustomConverter, XmlConverter> buildCustomConverterContainer() {
		return new CustomConverterContainer();
	}

	/**
	 * custom converter container
	 */
	protected class CustomConverterContainer
		extends ContextListContainer<OrmEclipseLinkCustomConverter, XmlConverter>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return CUSTOM_CONVERTERS_LIST;
		}
		@Override
		protected OrmEclipseLinkCustomConverter buildContextElement(XmlConverter resourceElement) {
			return OrmEclipseLinkConverterContainerImpl.this.buildCustomConverter(resourceElement);
		}
		@Override
		protected ListIterable<XmlConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlCustomConverters();
		}
		@Override
		protected XmlConverter getResourceElement(OrmEclipseLinkCustomConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}


	// ********** object type converters **********

	public ListIterable<OrmEclipseLinkObjectTypeConverter> getObjectTypeConverters() {
		return this.objectTypeConverterContainer.getContextElements();
	}

	public int getObjectTypeConvertersSize() {
		return this.objectTypeConverterContainer.getContextElementsSize();
	}

	public OrmEclipseLinkObjectTypeConverter addObjectTypeConverter() {
		return this.addObjectTypeConverter(this.getObjectTypeConvertersSize());
	}

	public OrmEclipseLinkObjectTypeConverter addObjectTypeConverter(int index) {
		XmlObjectTypeConverter xmlConverter = this.buildXmlObjectTypeConverter();
		OrmEclipseLinkObjectTypeConverter converter = this.objectTypeConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConvertersHolder.getObjectTypeConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlObjectTypeConverter buildXmlObjectTypeConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter converter) {
		this.removeObjectTypeConverter(this.objectTypeConverterContainer.indexOfContextElement((OrmEclipseLinkObjectTypeConverter) converter));
	}

	public void removeObjectTypeConverter(int index) {
		this.objectTypeConverterContainer.removeContextElement(index);
		this.xmlConvertersHolder.getObjectTypeConverters().remove(index);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		this.objectTypeConverterContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlConvertersHolder.getObjectTypeConverters().move(targetIndex, sourceIndex);
	}

	protected OrmEclipseLinkObjectTypeConverter buildObjectTypeConverter(XmlObjectTypeConverter xmlConverter) {
		return new OrmEclipseLinkObjectTypeConverter(this, xmlConverter);
	}

	protected void syncObjectTypeConverters() {
		this.objectTypeConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlObjectTypeConverter> getXmlObjectTypeConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlObjectTypeConverter>(this.xmlConvertersHolder.getObjectTypeConverters());
	}

	protected ContextListContainer<OrmEclipseLinkObjectTypeConverter, XmlObjectTypeConverter> buildObjectTypeConverterContainer() {
		return new ObjectTypeConverterContainer();
	}

	/**
	 * object type converter container
	 */
	protected class ObjectTypeConverterContainer
		extends ContextListContainer<OrmEclipseLinkObjectTypeConverter, XmlObjectTypeConverter>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return OBJECT_TYPE_CONVERTERS_LIST;
		}
		@Override
		protected OrmEclipseLinkObjectTypeConverter buildContextElement(XmlObjectTypeConverter resourceElement) {
			return OrmEclipseLinkConverterContainerImpl.this.buildObjectTypeConverter(resourceElement);
		}
		@Override
		protected ListIterable<XmlObjectTypeConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlObjectTypeConverters();
		}
		@Override
		protected XmlObjectTypeConverter getResourceElement(OrmEclipseLinkObjectTypeConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}

	// ********** struct converters **********

	public ListIterable<OrmEclipseLinkStructConverter> getStructConverters() {
		return this.structConverterContainer.getContextElements();
	}

	public int getStructConvertersSize() {
		return this.structConverterContainer.getContextElementsSize();
	}

	public OrmEclipseLinkStructConverter addStructConverter() {
		return this.addStructConverter(this.getStructConvertersSize());
	}

	public OrmEclipseLinkStructConverter addStructConverter(int index) {
		XmlStructConverter xmlConverter = this.buildXmlStructConverter();
		OrmEclipseLinkStructConverter converter = this.structConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConvertersHolder.getStructConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlStructConverter buildXmlStructConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
	}

	public void removeStructConverter(EclipseLinkStructConverter converter) {
		this.removeStructConverter(this.structConverterContainer.indexOfContextElement((OrmEclipseLinkStructConverter) converter));
	}

	public void removeStructConverter(int index) {
		this.structConverterContainer.removeContextElement(index);
		this.xmlConvertersHolder.getStructConverters().remove(index);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		this.structConverterContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlConvertersHolder.getStructConverters().move(targetIndex, sourceIndex);
	}

	protected OrmEclipseLinkStructConverter buildStructConverter(XmlStructConverter xmlConverter) {
		return new OrmEclipseLinkStructConverter(this, xmlConverter);
	}

	protected void syncStructConverters() {
		this.structConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlStructConverter> getXmlStructConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlStructConverter>(this.xmlConvertersHolder.getStructConverters());
	}

	protected ContextListContainer<OrmEclipseLinkStructConverter, XmlStructConverter> buildStructConverterContainer() {
		return new StructConverterContainer();
	}

	/**
	 * struct converter container
	 */
	protected class StructConverterContainer
		extends ContextListContainer<OrmEclipseLinkStructConverter, XmlStructConverter>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return STRUCT_CONVERTERS_LIST;
		}
		@Override
		protected OrmEclipseLinkStructConverter buildContextElement(XmlStructConverter resourceElement) {
			return OrmEclipseLinkConverterContainerImpl.this.buildStructConverter(resourceElement);
		}
		@Override
		protected ListIterable<XmlStructConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlStructConverters();
		}
		@Override
		protected XmlStructConverter getResourceElement(OrmEclipseLinkStructConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}


	// ********** type converters **********

	public ListIterable<OrmEclipseLinkTypeConverter> getTypeConverters() {
		return this.typeConverterContainer.getContextElements();
	}

	public int getTypeConvertersSize() {
		return this.typeConverterContainer.getContextElementsSize();
	}

	public OrmEclipseLinkTypeConverter addTypeConverter() {
		return this.addTypeConverter(this.getTypeConvertersSize());
	}

	public OrmEclipseLinkTypeConverter addTypeConverter(int index) {
		XmlTypeConverter xmlConverter = this.buildXmlTypeConverter();
		OrmEclipseLinkTypeConverter converter = this.typeConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConvertersHolder.getTypeConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlTypeConverter buildXmlTypeConverter() {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
	}

	public void removeTypeConverter(EclipseLinkTypeConverter converter) {
		this.removeTypeConverter(this.typeConverterContainer.indexOfContextElement((OrmEclipseLinkTypeConverter) converter));
	}

	public void removeTypeConverter(int index) {
		this.typeConverterContainer.removeContextElement(index);
		this.xmlConvertersHolder.getTypeConverters().remove(index);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		this.typeConverterContainer.moveContextElement(targetIndex, sourceIndex);
		this.xmlConvertersHolder.getTypeConverters().move(targetIndex, sourceIndex);
	}

	protected OrmEclipseLinkTypeConverter buildTypeConverter(XmlTypeConverter xmlConverter) {
		return new OrmEclipseLinkTypeConverter(this, xmlConverter);
	}

	protected void syncTypeConverters() {
		this.typeConverterContainer.synchronizeWithResourceModel();
	}

	protected ListIterable<XmlTypeConverter> getXmlTypeConverters() {
		// clone to reduce chance of concurrency problems
		return new LiveCloneListIterable<XmlTypeConverter>(this.xmlConvertersHolder.getTypeConverters());
	}

	protected ContextListContainer<OrmEclipseLinkTypeConverter, XmlTypeConverter> buildTypeConverterContainer() {
		return new TypeConverterContainer();
	}

	/**
	 * type converter container
	 */
	protected class TypeConverterContainer
		extends ContextListContainer<OrmEclipseLinkTypeConverter, XmlTypeConverter>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return TYPE_CONVERTERS_LIST;
		}
		@Override
		protected OrmEclipseLinkTypeConverter buildContextElement(XmlTypeConverter resourceElement) {
			return OrmEclipseLinkConverterContainerImpl.this.buildTypeConverter(resourceElement);
		}
		@Override
		protected ListIterable<XmlTypeConverter> getResourceElements() {
			return OrmEclipseLinkConverterContainerImpl.this.getXmlTypeConverters();
		}
		@Override
		protected XmlTypeConverter getResourceElement(OrmEclipseLinkTypeConverter contextElement) {
			return contextElement.getXmlConverter();
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

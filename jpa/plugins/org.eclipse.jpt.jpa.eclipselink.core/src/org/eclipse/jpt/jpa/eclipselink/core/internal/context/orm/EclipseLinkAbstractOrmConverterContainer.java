/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlTypeConverter;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class EclipseLinkAbstractOrmConverterContainer
	extends AbstractOrmXmlContextModel<EclipseLinkOrmConverterContainer.Parent>
	implements EclipseLinkOrmConverterContainer
{
	protected final XmlConverterContainer xmlConverterContainer;

	protected final ContextListContainer<EclipseLinkOrmCustomConverter, XmlConverter> customConverterContainer;
	protected final ContextListContainer<EclipseLinkOrmObjectTypeConverter, XmlObjectTypeConverter> objectTypeConverterContainer;
	protected final ContextListContainer<EclipseLinkOrmStructConverter, XmlStructConverter> structConverterContainer;
	protected final ContextListContainer<EclipseLinkOrmTypeConverter, XmlTypeConverter> typeConverterContainer;


	protected EclipseLinkAbstractOrmConverterContainer(EclipseLinkOrmConverterContainer.Parent parent, XmlConverterContainer xmlConverterContainer) {
		super(parent);
		this.xmlConverterContainer = xmlConverterContainer;

		this.customConverterContainer = this.buildCustomConverterContainer();
		this.objectTypeConverterContainer = this.buildObjectTypeConverterContainer();
		this.structConverterContainer = this.buildStructConverterContainer();
		this.typeConverterContainer = this.buildTypeConverterContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.syncCustomConverters(monitor);
		this.syncObjectTypeConverters(monitor);
		this.syncStructConverters(monitor);
		this.syncTypeConverters(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.updateModels(this.getCustomConverters(), monitor);
		this.updateModels(this.getObjectTypeConverters(), monitor);
		this.updateModels(this.getStructConverters(), monitor);
		this.updateModels(this.getTypeConverters(), monitor);
	}


	// ********** custom converters **********

	public ListIterable<EclipseLinkOrmCustomConverter> getCustomConverters() {
		return this.customConverterContainer;
	}

	public int getCustomConvertersSize() {
		return this.customConverterContainer.size();
	}

	public EclipseLinkOrmCustomConverter addCustomConverter(String name) {
		return this.addCustomConverter(name, this.getCustomConvertersSize());
	}

	public EclipseLinkOrmCustomConverter addCustomConverter(String name, int index) {
		XmlConverter xmlConverter = this.buildXmlCustomConverter(name);
		EclipseLinkOrmCustomConverter converter = this.customConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConverterContainer.getConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlConverter buildXmlCustomConverter(String name) {
		XmlConverter converter = EclipseLinkOrmFactory.eINSTANCE.createXmlConverter();
		converter.setName(name);
		return converter;
	}

	public void removeCustomConverter(EclipseLinkCustomConverter converter) {
		this.removeCustomConverter(this.customConverterContainer.indexOf((EclipseLinkOrmCustomConverter) converter));
	}

	public void removeCustomConverter(int index) {
		this.customConverterContainer.remove(index);
		this.xmlConverterContainer.getConverters().remove(index);
	}

	public void moveCustomConverter(int targetIndex, int sourceIndex) {
		this.customConverterContainer.move(targetIndex, sourceIndex);
		this.xmlConverterContainer.getConverters().move(targetIndex, sourceIndex);
	}

	protected EclipseLinkOrmCustomConverter buildCustomConverter(XmlConverter xmlConverter) {
		return new EclipseLinkOrmCustomConverter(this, xmlConverter);
	}

	protected void syncCustomConverters(IProgressMonitor monitor) {
		this.customConverterContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlConverter> getXmlCustomConverters() {
		// clone to reduce chance of concurrency problems
		return IterableTools.downcast(IterableTools.cloneLive(this.getXmlConverters()));
	}

	protected List<org.eclipse.jpt.jpa.core.resource.orm.XmlConverter> getXmlConverters() {
		return this.xmlConverterContainer.getConverters();
	}

	protected ContextListContainer<EclipseLinkOrmCustomConverter, XmlConverter> buildCustomConverterContainer() {
		return this.buildSpecifiedContextListContainer(CUSTOM_CONVERTERS_LIST, new CustomConverterContainerAdapter());
	}

	/**
	 * custom converter container adapter
	 */
	public class CustomConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkOrmCustomConverter, XmlConverter>
	{
		public EclipseLinkOrmCustomConverter buildContextElement(XmlConverter resourceElement) {
			return EclipseLinkAbstractOrmConverterContainer.this.buildCustomConverter(resourceElement);
		}
		public ListIterable<XmlConverter> getResourceElements() {
			return EclipseLinkAbstractOrmConverterContainer.this.getXmlCustomConverters();
		}
		public XmlConverter extractResourceElement(EclipseLinkOrmCustomConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}


	// ********** object type converters **********

	public ListIterable<EclipseLinkOrmObjectTypeConverter> getObjectTypeConverters() {
		return this.objectTypeConverterContainer;
	}

	public int getObjectTypeConvertersSize() {
		return this.objectTypeConverterContainer.size();
	}

	public EclipseLinkOrmObjectTypeConverter addObjectTypeConverter(String name) {
		return this.addObjectTypeConverter(name, this.getObjectTypeConvertersSize());
	}

	public EclipseLinkOrmObjectTypeConverter addObjectTypeConverter(String name, int index) {
		XmlObjectTypeConverter xmlConverter = this.buildXmlObjectTypeConverter(name);
		EclipseLinkOrmObjectTypeConverter converter = this.objectTypeConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConverterContainer.getObjectTypeConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlObjectTypeConverter buildXmlObjectTypeConverter(String name) {
		XmlObjectTypeConverter converter = EclipseLinkOrmFactory.eINSTANCE.createXmlObjectTypeConverter();
		converter.setName(name);
		return converter;
	}

	public void removeObjectTypeConverter(EclipseLinkObjectTypeConverter converter) {
		this.removeObjectTypeConverter(this.objectTypeConverterContainer.indexOf((EclipseLinkOrmObjectTypeConverter) converter));
	}

	public void removeObjectTypeConverter(int index) {
		this.objectTypeConverterContainer.remove(index);
		this.xmlConverterContainer.getObjectTypeConverters().remove(index);
	}

	public void moveObjectTypeConverter(int targetIndex, int sourceIndex) {
		this.objectTypeConverterContainer.move(targetIndex, sourceIndex);
		this.xmlConverterContainer.getObjectTypeConverters().move(targetIndex, sourceIndex);
	}

	protected EclipseLinkOrmObjectTypeConverter buildObjectTypeConverter(XmlObjectTypeConverter xmlConverter) {
		return new EclipseLinkOrmObjectTypeConverter(this, xmlConverter);
	}

	protected void syncObjectTypeConverters(IProgressMonitor monitor) {
		this.objectTypeConverterContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlObjectTypeConverter> getXmlObjectTypeConverters() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlConverterContainer.getObjectTypeConverters());
	}

	protected ContextListContainer<EclipseLinkOrmObjectTypeConverter, XmlObjectTypeConverter> buildObjectTypeConverterContainer() {
		return this.buildSpecifiedContextListContainer(OBJECT_TYPE_CONVERTERS_LIST, new ObjectTypeConverterContainerAdapter());
	}

	/**
	 * object type converter container adapter
	 */
	public class ObjectTypeConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkOrmObjectTypeConverter, XmlObjectTypeConverter>
	{
		public EclipseLinkOrmObjectTypeConverter buildContextElement(XmlObjectTypeConverter resourceElement) {
			return EclipseLinkAbstractOrmConverterContainer.this.buildObjectTypeConverter(resourceElement);
		}
		public ListIterable<XmlObjectTypeConverter> getResourceElements() {
			return EclipseLinkAbstractOrmConverterContainer.this.getXmlObjectTypeConverters();
		}
		public XmlObjectTypeConverter extractResourceElement(EclipseLinkOrmObjectTypeConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}

	// ********** struct converters **********

	public ListIterable<EclipseLinkOrmStructConverter> getStructConverters() {
		return this.structConverterContainer;
	}

	public int getStructConvertersSize() {
		return this.structConverterContainer.size();
	}

	public EclipseLinkOrmStructConverter addStructConverter(String name) {
		return this.addStructConverter(name, this.getStructConvertersSize());
	}

	public EclipseLinkOrmStructConverter addStructConverter(String name, int index) {
		XmlStructConverter xmlConverter = this.buildXmlStructConverter(name);
		EclipseLinkOrmStructConverter converter = this.structConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConverterContainer.getStructConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlStructConverter buildXmlStructConverter(String name) {
		XmlStructConverter converter = EclipseLinkOrmFactory.eINSTANCE.createXmlStructConverter();
		converter.setName(name);
		return converter;
	}

	public void removeStructConverter(EclipseLinkStructConverter converter) {
		this.removeStructConverter(this.structConverterContainer.indexOf((EclipseLinkOrmStructConverter) converter));
	}

	public void removeStructConverter(int index) {
		this.structConverterContainer.remove(index);
		this.xmlConverterContainer.getStructConverters().remove(index);
	}

	public void moveStructConverter(int targetIndex, int sourceIndex) {
		this.structConverterContainer.move(targetIndex, sourceIndex);
		this.xmlConverterContainer.getStructConverters().move(targetIndex, sourceIndex);
	}

	protected EclipseLinkOrmStructConverter buildStructConverter(XmlStructConverter xmlConverter) {
		return new EclipseLinkOrmStructConverter(this, xmlConverter);
	}

	protected void syncStructConverters(IProgressMonitor monitor) {
		this.structConverterContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlStructConverter> getXmlStructConverters() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlConverterContainer.getStructConverters());
	}

	protected ContextListContainer<EclipseLinkOrmStructConverter, XmlStructConverter> buildStructConverterContainer() {
		return this.buildSpecifiedContextListContainer(STRUCT_CONVERTERS_LIST, new StructConverterContainerAdapter());
	}

	/**
	 * struct converter container adapter
	 */
	public class StructConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkOrmStructConverter, XmlStructConverter>
	{
		public EclipseLinkOrmStructConverter buildContextElement(XmlStructConverter resourceElement) {
			return EclipseLinkAbstractOrmConverterContainer.this.buildStructConverter(resourceElement);
		}
		public ListIterable<XmlStructConverter> getResourceElements() {
			return EclipseLinkAbstractOrmConverterContainer.this.getXmlStructConverters();
		}
		public XmlStructConverter extractResourceElement(EclipseLinkOrmStructConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}


	// ********** type converters **********

	public ListIterable<EclipseLinkOrmTypeConverter> getTypeConverters() {
		return this.typeConverterContainer;
	}

	public int getTypeConvertersSize() {
		return this.typeConverterContainer.size();
	}

	public EclipseLinkOrmTypeConverter addTypeConverter(String name) {
		return this.addTypeConverter(name, this.getTypeConvertersSize());
	}

	public EclipseLinkOrmTypeConverter addTypeConverter(String name, int index) {
		XmlTypeConverter xmlConverter = this.buildXmlTypeConverter(name);
		EclipseLinkOrmTypeConverter converter = this.typeConverterContainer.addContextElement(index, xmlConverter);
		this.xmlConverterContainer.getTypeConverters().add(index, xmlConverter);
		return converter;
	}

	protected XmlTypeConverter buildXmlTypeConverter(String name) {
		XmlTypeConverter converter = EclipseLinkOrmFactory.eINSTANCE.createXmlTypeConverter();
		converter.setName(name);
		return converter;
	}

	public void removeTypeConverter(EclipseLinkTypeConverter converter) {
		this.removeTypeConverter(this.typeConverterContainer.indexOf((EclipseLinkOrmTypeConverter) converter));
	}

	public void removeTypeConverter(int index) {
		this.typeConverterContainer.remove(index);
		this.xmlConverterContainer.getTypeConverters().remove(index);
	}

	public void moveTypeConverter(int targetIndex, int sourceIndex) {
		this.typeConverterContainer.move(targetIndex, sourceIndex);
		this.xmlConverterContainer.getTypeConverters().move(targetIndex, sourceIndex);
	}

	protected EclipseLinkOrmTypeConverter buildTypeConverter(XmlTypeConverter xmlConverter) {
		return new EclipseLinkOrmTypeConverter(this, xmlConverter);
	}

	protected void syncTypeConverters(IProgressMonitor monitor) {
		this.typeConverterContainer.synchronizeWithResourceModel(monitor);
	}

	protected ListIterable<XmlTypeConverter> getXmlTypeConverters() {
		// clone to reduce chance of concurrency problems
		return IterableTools.cloneLive(this.xmlConverterContainer.getTypeConverters());
	}

	protected ContextListContainer<EclipseLinkOrmTypeConverter, XmlTypeConverter> buildTypeConverterContainer() {
		return this.buildSpecifiedContextListContainer(TYPE_CONVERTERS_LIST, new TypeConverterContainerAdapter());
	}

	/**
	 * type converter container adapter
	 */
	public class TypeConverterContainerAdapter
		extends AbstractContainerAdapter<EclipseLinkOrmTypeConverter, XmlTypeConverter>
	{
		public EclipseLinkOrmTypeConverter buildContextElement(XmlTypeConverter resourceElement) {
			return EclipseLinkAbstractOrmConverterContainer.this.buildTypeConverter(resourceElement);
		}
		public ListIterable<XmlTypeConverter> getResourceElements() {
			return EclipseLinkAbstractOrmConverterContainer.this.getXmlTypeConverters();
		}
		public XmlTypeConverter extractResourceElement(EclipseLinkOrmTypeConverter contextElement) {
			return contextElement.getXmlConverter();
		}
	}


	// ********** refactoring **********

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
			this.createRenameObjectTypeConverterEdits(originalType, newName),
			this.createRenameTypeConverterEdits(originalType, newName),
			this.createRenameStructConverterEdits(originalType, newName),
			this.createRenameCustomConverterEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createRenameObjectTypeConverterEdits(IType originalType, String newName) {
		return IterableTools.children(getObjectTypeConverters(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createRenameTypeConverterEdits(IType originalType, String newName) {
		return IterableTools.children(getTypeConverters(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createRenameStructConverterEdits(IType originalType, String newName) {
		return IterableTools.children(getStructConverters(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createRenameCustomConverterEdits(IType originalType, String newName) {
		return IterableTools.children(getCustomConverters(), new TypeRefactoringParticipant.RenameTypeEditsTransformer(originalType, newName));
	}

	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
			this.createMoveObjectTypeConverterEdits(originalType, newPackage),
			this.createMoveTypeConverterEdits(originalType, newPackage),
			this.createMoveStructConverterEdits(originalType, newPackage),
			this.createMoveCustomConverterEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMoveObjectTypeConverterEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(getObjectTypeConverters(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMoveTypeConverterEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(getTypeConverters(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMoveStructConverterEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(getStructConverters(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createMoveCustomConverterEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.children(getCustomConverters(), new TypeRefactoringParticipant.MoveTypeEditsTransformer(originalType, newPackage));
	}


	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
			this.createObjectTypeConverterRenamePackageEdits(originalPackage, newName),
			this.createTypeConverterRenamePackageEdits(originalPackage, newName),
			this.createStructConverterRenamePackageEdits(originalPackage, newName),
			this.createCustomConverterRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createObjectTypeConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(getObjectTypeConverters(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createTypeConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(getTypeConverters(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createStructConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(getStructConverters(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createCustomConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.children(getCustomConverters(), new TypeRefactoringParticipant.RenamePackageEditsTransformer(originalPackage, newName));
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
		TextRange textRange = this.xmlConverterContainer.getValidationTextRange();
		return (textRange != null) ? textRange : this.parent.getValidationTextRange();
	}


	// ********** misc **********

	@SuppressWarnings("unchecked")
	public Iterable<EclipseLinkConverter> getConverters() {
		return IterableTools.<EclipseLinkConverter>concatenate(
					this.getCustomConverters(),
					this.getObjectTypeConverters(),
					this.getStructConverters(),
					this.getTypeConverters()
				);
	}

	public int getConvertersSize() {
		return this.getCustomConvertersSize()
			+ this.getObjectTypeConvertersSize()
			+ this.getStructConvertersSize()
			+ this.getTypeConvertersSize();
	}

	public int getMaximumAllowedConverters() {
		return this.parent.getMaximumAllowedConverters();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (EclipseLinkOrmCustomConverter converter : this.customConverterContainer) {
			result = converter.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		for (EclipseLinkOrmStructConverter converter : this.structConverterContainer) {
			result = converter.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		for (EclipseLinkOrmObjectTypeConverter converter : this.objectTypeConverterContainer) {
			result = converter.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		for (EclipseLinkOrmTypeConverter converter : this.typeConverterContainer) {
			result = converter.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
}

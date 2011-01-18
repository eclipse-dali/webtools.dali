/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.ReadOnlyTable;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.GenericMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlIdClassContainer;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> mapped superclass
 */
public abstract class AbstractOrmMappedSuperclass<X extends XmlMappedSuperclass>
	extends AbstractOrmTypeMapping<X>
	implements OrmMappedSuperclass, OrmIdClassReference.Owner
{
	protected final OrmIdClassReference idClassReference;


	protected AbstractOrmMappedSuperclass(OrmPersistentType parent, X xmlMappedSuperclass) {
		super(parent, xmlMappedSuperclass);
		this.idClassReference = this.buildIdClassReference();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.idClassReference.synchronizeWithResourceModel();
	}

	@Override
	public void update() {
		super.update();
		this.idClassReference.update();
	}


	// ********** key **********

	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}


	// ********** id class **********

	public OrmIdClassReference getIdClassReference() {
		return this.idClassReference;
	}

	protected OrmIdClassReference buildIdClassReference() {
		return new GenericOrmIdClassReference(this, this);
	}

	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
	}

	public XmlIdClassContainer getXmlIdClassContainer() {
		return this.getXmlTypeMapping();
	}

	public JavaIdClassReference getJavaIdClassReferenceForDefaults() {
		JavaMappedSuperclass javaMappedSuperclass = this.getJavaTypeMappingForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getIdClassReference();
	}


	// ********** entity mappings **********

	public int getXmlSequence() {
		return 0;
	}

	public void addXmlTypeMappingTo(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().add(this.xmlTypeMapping);
	}

	public void removeXmlTypeMappingFrom(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().remove(this.xmlTypeMapping);
	}


	// ********** Java **********

	@Override
	public JavaMappedSuperclass getJavaTypeMapping() {
		return (JavaMappedSuperclass) super.getJavaTypeMapping();
	}

	@Override
	public JavaMappedSuperclass getJavaTypeMappingForDefaults() {
		return (JavaMappedSuperclass) super.getJavaTypeMappingForDefaults();
	}


	// ********** tables **********

	public Iterator<ReadOnlyTable> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<ReadOnlyTable> allAssociatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allAssociatedTableNames() {
		return EmptyIterator.instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenameTypeEdits(originalType, newName),
				this.createIdClassReplaceTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createIdClassReplaceTypeEdits(IType originalType, String newName) {
		return this.idClassReference.createRenameTypeEdits(originalType, newName);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createIdClassMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createIdClassMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.idClassReference.createMoveTypeEdits(originalType, newPackage);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createIdClassRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createIdClassRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.idClassReference.createRenamePackageEdits(originalPackage, newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validatePrimaryKey(messages, reporter);
	}

	@Override
	public boolean validatesAgainstDatabase() {
		return false;
	}

	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		this.buildPrimaryKeyValidator().validate(messages, reporter);
	}

	protected JptValidator buildPrimaryKeyValidator() {
		return new GenericMappedSuperclassPrimaryKeyValidator(this, this.buildTextRangeResolver());
		// TODO - JPA 2.0 validation
	}

	@Override
	protected PrimaryKeyTextRangeResolver buildTextRangeResolver() {
		return new OrmMappedSuperclassTextRangeResolver(this);
	}
}

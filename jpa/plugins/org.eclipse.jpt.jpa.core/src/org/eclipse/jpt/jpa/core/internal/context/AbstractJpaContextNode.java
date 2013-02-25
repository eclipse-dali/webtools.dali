/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import java.util.List;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaModel;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJpaContextNode
	extends AbstractJpaModel
	implements JpaContextNode
{
	protected AbstractJpaContextNode(JpaModel parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		// NOP
	}

	/**
	 * convenience method
	 */
	protected void synchronizeNodesWithResourceModel(Iterable<? extends JpaContextNode> nodes) {
		for (JpaContextNode node : nodes) {
			node.synchronizeWithResourceModel();
		}
	}

	public void update() {
		// NOP
	}

	/**
	 * convenience method
	 */
	protected void updateNodes(Iterable<? extends JpaContextNode> nodes) {
		for (JpaContextNode node : nodes) {
			node.update();
		}
	}


	// ********** containment hierarchy **********

	/**
	 * covariant override
	 */
	@Override
	public JpaContextNode getParent() {
		return (JpaContextNode) super.getParent();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode#getResourceType() AbstractJavaJpaContextNode}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJarFile#getResourceType() GenericJarFile}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml#getResourceType() GenericOrmXml}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml#getResourceType() GenericPersistenceXml}
	 * </ul>
	 */
	public JptResourceType getResourceType() {
		return this.getParent().getResourceType();
	}
	
	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#getPersistenceUnit() AbstractPersistenceUnit}
	 * to return itself
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericRootContextNode#getPersistenceUnit() GenericRootContextNode}
	 * to return <code>null</code>
	 * </ul>
	 */
	public PersistenceUnit getPersistenceUnit() {
		return this.getParent().getPersistenceUnit();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings#getMappingFileRoot() AbstractEntityMappings}
	 * to return itself
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericRootContextNode#getMappingFileRoot() GenericRootContextNode}
	 * to return <code>null</code>
	 * </ul>
	 */
	public MappingFile.Root getMappingFileRoot() {
		return this.getParent().getMappingFileRoot();
	}


	// ********** validation **********

	/**
	 * All subclass implementations should be 
	 * preceded by a "super" call to this method.
	 */
	public void validate(List<IMessage> messages, IReporter reporter) {
		if (reporter.isCancelled()) {
			throw new ValidationCancelledException();
		}
	}

	/**
	 * Return the specified text range if it is not <code>null</code>; if it is
	 * <code>null</code>, return the node's validation text range.
	 */
	protected TextRange getValidationTextRange(TextRange textRange) {
		return (textRange != null) ? textRange : this.getValidationTextRange();
	}

	/**
	 * Validate the specified node if it is not <code>null</code>.
	 */
	protected void validateNode(JpaContextNode node, List<IMessage> messages, IReporter reporter) {
		if (node != null) {
			node.validate(messages, reporter);
		}
	}

	/**
	 * Validate the specified nodes.
	 */
	protected void validateNodes(Iterable<? extends JpaContextNode> nodes, List<IMessage> messages, IReporter reporter) {
		for (JpaContextNode node : nodes) {
			node.validate(messages, reporter);
		}
	}

	/**
	 * @see #buildValidationMessage(JpaModel, ValidationMessage)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(ValidationMessage message) {
		return this.buildValidationMessage(this, message);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(IResource, ValidationMessage)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(JpaModel target, ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(target.getResource(), message);
	}

	/**
	 * @see #buildValidationMessage(JpaModel, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, Object... args) {
		return this.buildValidationMessage(this, message, args);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(IResource, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(JpaModel target, ValidationMessage message, Object... args) {
		return ValidationMessageTools.buildValidationMessage(target.getResource(), message, args);
	}

	/**
	 * @see #buildValidationMessage(JpaModel, TextRange, ValidationMessage)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(TextRange textRange, ValidationMessage message) {
		return this.buildValidationMessage(this, textRange, message);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(IResource, TextRange, ValidationMessage)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(JpaModel target, TextRange textRange, ValidationMessage message) {
		return ValidationMessageTools.buildValidationMessage(target.getResource(), textRange, message);
	}

	/**
	 * @see #buildValidationMessage(JpaModel, TextRange, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(TextRange textRange, ValidationMessage message, Object... args) {
		return this.buildValidationMessage(this, textRange, message, args);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(IResource, TextRange, ValidationMessage, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildValidationMessage(JpaModel target, TextRange textRange, ValidationMessage message, Object... args) {
		return ValidationMessageTools.buildValidationMessage(target.getResource(), textRange, message, args);
	}


	// *********** completion proposals ***********

	public Iterable<String> getCompletionProposals(int pos) {
		if (this.connectionProfileIsActive()) {
			Iterable<String> result = this.getConnectedCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	/**
	 * This method is called if the database is connected, allowing us to
	 * get candidates from the various database tables etc.
	 * This method should <em>not</em> be cascaded to "child" objects; it should
	 * only return candidates for the current object. The cascading is
	 * handled by {@link #getCompletionProposals(int)}.
	 */
	@SuppressWarnings("unused")
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		return null;
	}


	// ********** database stuff **********

	public Schema getContextDefaultDbSchema() {
		SchemaContainer dbSchemaContainer = this.getContextDefaultDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getContextDefaultSchema());
	}

	protected String getContextDefaultSchema() {
		MappingFile.Root mfr = this.getMappingFileRoot();
		return (mfr != null) ? mfr.getSchema() : this.getPersistenceUnit().getDefaultSchema();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getContextDefaultDbSchemaContainer() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getContextDefaultDbCatalog() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}

	protected String getContextDefaultCatalog() {
		MappingFile.Root mfr = this.getMappingFileRoot();
		return (mfr != null) ? mfr.getCatalog() : this.getPersistenceUnit().getDefaultCatalog();
	}
	
}

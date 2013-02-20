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
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaNode;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJpaContextNode
	extends AbstractJpaNode
	implements JpaContextNode
{
	protected AbstractJpaContextNode(JpaNode parent) {
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
	 * @see #buildErrorValidationMessage(ValidationMessage, JpaNode)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message) {
		return this.buildErrorValidationMessage(message, this);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JpaNode target) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource());
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JpaNode)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity) {
		return this.buildValidationMessage(message, defaultSeverity, this);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JpaNode target) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource());
	}

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JpaNode, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, Object... args) {
		return this.buildErrorValidationMessage(message, this, args);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JpaNode target, Object... args) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource(), args);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JpaNode, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, Object... args) {
		return this.buildValidationMessage(message, defaultSeverity, this, args);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JpaNode target, Object... args) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource(), args);
	}

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JpaNode, TextRange)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, TextRange textRange) {
		return this.buildErrorValidationMessage(message, this, textRange);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource, TextRange)
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JpaNode target, TextRange textRange) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource(), textRange);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JpaNode, TextRange)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, TextRange textRange) {
		return this.buildValidationMessage(message, defaultSeverity, this, textRange);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource, TextRange)
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JpaNode target, TextRange textRange) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource(), textRange);
	}

	/**
	 * @see #buildErrorValidationMessage(ValidationMessage, JpaNode, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, TextRange textRange, Object... args) {
		return this.buildErrorValidationMessage(message, this, textRange, args);
	}

	/**
	 * @see ValidationMessageTools#buildErrorValidationMessage(ValidationMessage, IResource, TextRange, Object[])
	 * @see IMessage#HIGH_SEVERITY
	 */
	protected IMessage buildErrorValidationMessage(ValidationMessage message, JpaNode target, TextRange textRange, Object... args) {
		return ValidationMessageTools.buildErrorValidationMessage(message, target.getResource(), textRange, args);
	}

	/**
	 * @see #buildValidationMessage(ValidationMessage, int, JpaNode, TextRange, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, TextRange textRange, Object... args) {
		return this.buildValidationMessage(message, defaultSeverity, this, textRange, args);
	}

	/**
	 * @see ValidationMessageTools#buildValidationMessage(ValidationMessage, int, IResource, TextRange, Object[])
	 */
	protected IMessage buildValidationMessage(ValidationMessage message, int defaultSeverity, JpaNode target, TextRange textRange, Object... args) {
		return ValidationMessageTools.buildValidationMessage(message, defaultSeverity, target.getResource(), textRange, args);
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

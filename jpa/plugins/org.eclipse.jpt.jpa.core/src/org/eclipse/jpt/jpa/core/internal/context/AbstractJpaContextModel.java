/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.ValidationMessageTools;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.ValidationMessage;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaModel;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jst.j2ee.model.internal.validation.ValidationCancelledException;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractJpaContextModel<P extends JpaContextModel>
	extends AbstractJpaModel<P>
	implements JpaContextModel
{
	protected AbstractJpaContextModel(P parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		// NOP
	}

	/**
	 * convenience method
	 */
	protected void synchronizeModelsWithResourceModel(Iterable<? extends JpaContextModel> models) {
		for (JpaContextModel model : models) {
			model.synchronizeWithResourceModel();
		}
	}

	public void update(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}

	/**
	 * convenience method
	 */
	protected void updateModels(Iterable<? extends JpaContextModel> models, IProgressMonitor monitor) {
		for (JpaContextModel model : models) {
			model.update(monitor);
		}
	}


	// ********** containment hierarchy **********

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaContextModel#getResourceType() AbstractJavaJpaContextModel}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJarFile#getResourceType() GenericJarFile}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml#getResourceType() GenericOrmXml}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml#getResourceType() GenericPersistenceXml}
	 * </ul>
	 */
	public JptResourceType getResourceType() {
		return this.parent.getResourceType();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#getPersistenceUnit() AbstractPersistenceUnit}
	 * to return itself
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericContextRoot#getPersistenceUnit() GenericContextModelRoot}
	 * to return <code>null</code>
	 * </ul>
	 */
	public PersistenceUnit getPersistenceUnit() {
		return this.parent.getPersistenceUnit();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings#getMappingFileRoot() AbstractEntityMappings}
	 * to return itself
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericContextRoot#getMappingFileRoot() GenericContextModelRoot}
	 * to return <code>null</code>
	 * </ul>
	 */
	public MappingFile.Root getMappingFileRoot() {
		return this.parent.getMappingFileRoot();
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
	 * <code>null</code>, return the model's validation text range.
	 * Typically, the specified text range is for one of the model's children.
	 */
	protected TextRange getValidationTextRange(TextRange textRange) {
		return (textRange != null) ? textRange : this.getValidationTextRange();
	}

	/**
	 * Validate the specified model if it is not <code>null</code>.
	 */
	protected void validateModel(JpaContextModel model, List<IMessage> messages, IReporter reporter) {
		if (model != null) {
			model.validate(messages, reporter);
		}
	}

	/**
	 * Validate the specified models.
	 */
	protected void validateModels(Iterable<? extends JpaContextModel> models, List<IMessage> messages, IReporter reporter) {
		for (JpaContextModel model : models) {
			model.validate(messages, reporter);
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


	// ********** containers **********

	/**
	 * Adapter used to synchronize a collection of context models with the
	 * corresponding collection of resource models.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	public static abstract class Container<C, R>
		implements ListIterable<C>
	{
		/**
		 * "Context" elements.
		 */
		protected final Vector<C> elements = new Vector<C>();

		/**
		 * Aspect name used for event notification when the
		 * container's contents change.
		 */
		protected final String aspectName;

		protected final Adapter<C, R> adapter;


		protected Container(String aspectName, Adapter<C, R> adapter) {
			super();
			this.aspectName = aspectName;
			this.adapter = adapter;
		}

		/**
		 * Subclasses call this as necessary.
		 */
		protected void initialize() {
			for (R resourceElement : this.adapter.getResourceElements()) {
				this.elements.add(this.adapter.buildContextElement(resourceElement));
			}
		}

		public C get(int index) {
			return this.elements.get(index);
		}

		public ListIterator<C> iterator() {
			return IteratorTools.clone(this.elements);
		}

		/**
		 * Return the size of the context elements collection
		 */
		public int size() {
			return this.elements.size();
		}

		/**
		 * Add a context element for the specified resource element at the
		 * specified index.
		 */
		public C addContextElement(int index, R resourceElement) {
			return this.add(index, this.adapter.buildContextElement(resourceElement));
		}

		/**
		 * Add the specified context element to the container at the specified
		 * index.
		 */
		protected abstract C add(int index, C element);

		/**
		 * Add context elements for the specified resource elements at the
		 * specified index.
		 */
		public Iterable<C> addContextElements(int index, Iterable<R> resourceElements) {
			ArrayList<C> contextElements = new ArrayList<C>();
			for (R resourceElement : resourceElements) {
				contextElements.add(this.adapter.buildContextElement(resourceElement));
			}
			return this.addAll(index, contextElements);
		}

		/**
		 * Add the specified context elements to the collection.
		 */
		protected abstract Iterable<C> addAll(int index, Iterable<C> contextElements);

		/**
		 * Remove the specified context element from the container.
		 */
		public abstract void remove(C element);

		/**
		 * Remove the specified context elements from the container.
		 */
		public abstract void removeAll(Iterable<C> contextElements);

		protected abstract void move(int index, C element);

		@Override
		public String toString() {
			return this.elements.toString();
		}

		/**
		 * Adapter used by the container to convert resource and context
		 * elements.
		 */
		public interface Adapter<C, R> {
			/**
			 * Return the current list of resource elements.
			 * The context elements are synchronized with this list.
			 */
			Iterable<R> getResourceElements();

			/**
			 * Return the specified context element's resource element.
			 * @see #buildContextElement(Object)
			 */
			R extractResourceElement(C contextElement);

			/**
			 * Build a context element for the specified resource element.
			 * @see #extractResourceElement(Object)
			 */
			C buildContextElement(R resourceElement);
		}
	}

	public abstract class AbstractContainerAdapter<C, R>
		implements Container.Adapter<C, R>
	{
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	/**
	 * Adapter used to synchronize a collection of context models with the
	 * corresponding collection of resource models.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	public abstract class ContextContainer<C extends JpaContextModel, R>
		extends Container<C, R>
	{
		protected ContextContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
		}

		/**
		 * Synchronize the context container with its
		 * corresponding resource container: moving, removing, and adding elements
		 * as necessary.
		 * <p>
		 * We can do this because:<ul>
		 * <li>the XML translators will <em>move</em> the EMF elements when
		 * appropriate (as opposed to simply rebuilding them in place).
		 * <li>the Java resource model will re-use existing annotations when
		 * appropriate (as opposed to simply rebuilding them in place).
		 * </ul>
		 */
		public void synchronizeWithResourceModel() {
			this.sync(true, null);  // true = sync
		}

		/**
		 * @see #synchronizeWithResourceModel()
		 */
		public void update(IProgressMonitor monitor) {
			this.sync(false, monitor);  // false = update
		}

		/**
		 * The specified <code>sync</code> flag controls whether any surviving
		 * context nodes are either <em>synchronized</em> (<code>true</code>) or
		 * <em>updated</em> (<code>false</code>).
		 */
		protected void sync(boolean sync, IProgressMonitor monitor) {
			@SuppressWarnings("unchecked")
			HashSet<C> contextElements = (HashSet<C>) CollectionTools.hashSet(this.elements.toArray());
			ArrayList<C> contextElementsToSync = new ArrayList<C>(contextElements.size());
			int resourceIndex = 0;

			for (R resourceElement : this.adapter.getResourceElements()) {
				boolean match = false;
				for (Iterator<C> stream = contextElements.iterator(); stream.hasNext(); ) {
					C contextElement = stream.next();
					if (ObjectTools.equals(this.adapter.extractResourceElement(contextElement), resourceElement)) {
						// we don't know the source index because the element has been moved by previously moved elements
						this.move(resourceIndex, contextElement);
						stream.remove();
						// TODO perform update here someday...
						contextElementsToSync.add(contextElement);
						match = true;
						break;
					}
				}
				if ( ! match) {
					// added elements are sync'ed during construction or will be
					// updated during the next "update" (which is triggered by
					// their addition to the model)
					this.addContextElement(resourceIndex, resourceElement);
				}
				resourceIndex++;
			}
			// remove any leftover context elements
			for (C contextElement : contextElements) {
				this.remove(contextElement);
			}
			// TODO bjv
			// take care of the structural changes before sync'ing the remaining elements;
			// we might be able to do this inline once we get rid of the "list change" events
			// and go with only add, remove, etc. list events
			// (these syncs will trigger "list change" events with list aspect adapters, which
			// trigger refreshes of UI adapters (e.g. TableModelAdapter) which will prematurely
			// discover any structural changes... :( )
			// see ItemAspectListValueModelAdapter.itemAspectChanged(EventObject)
			for (C contextElement : contextElementsToSync) {
				if (sync) {
					contextElement.synchronizeWithResourceModel();
				} else {
					contextElement.update(monitor);
				}
			}
		}
	}


	/**
	 * Adapter used to synchronize a <em>collection</em> of context models with the
	 * corresponding <em>collection</em> of resource models.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	public abstract class ContextCollectionContainer<C extends JpaContextModel, R>
		extends ContextContainer<C, R>
	{
		protected ContextCollectionContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
		}

		@Override
		protected C add(int index, C element) {
			// ignore the index - not a list
			AbstractJpaContextModel.this.addItemToCollection(element, this.elements, this.aspectName);
			return element;
		}

		@Override
		protected Iterable<C> addAll(int index, Iterable<C> contextElements) {
			// ignore the index - not a list
			AbstractJpaContextModel.this.addItemsToCollection(contextElements, this.elements, this.aspectName);
			return contextElements;
		}

		@Override
		public void remove(C element) {
			AbstractJpaContextModel.this.removeItemFromCollection(element, this.elements, this.aspectName);
		}

		@Override
		public void removeAll(Iterable<C> contextElements) {
			AbstractJpaContextModel.this.removeItemsFromCollection(contextElements, this.elements, this.aspectName);
		}

		@Override
		protected void move(int index, C element) {
			// NOP - not a list
		}
	}

	// open up accessibility to container classes
	@Override
	protected <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		return super.addItemToCollection(item, collection, collectionName);
	}
	@Override
	protected <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return super.addItemsToCollection(items, collection, collectionName);
	}
	@Override
	protected boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		return super.removeItemFromCollection(item, collection, collectionName);
	}
	@Override
	protected boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return super.removeItemsFromCollection(items, collection, collectionName);
	}


	/**
	 * Use this container for a collection of <em>specified</em> elements.
	 * The container is initialized from the resource model upon construction.
	 * @see AbstractJpaContextModel.SpecifiedContextListContainer
	 * @see #buildSpecifiedContextCollectionContainer(String, AbstractJpaContextModel.Container.Adapter)
	 */
	public class SpecifiedContextCollectionContainer<C extends JpaContextModel, R>
		extends ContextCollectionContainer<C, R>
	{
		public SpecifiedContextCollectionContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
			this.initialize();
		}
	}

	protected <C extends JpaContextModel, R> SpecifiedContextCollectionContainer<C, R> buildSpecifiedContextCollectionContainer(String aspectName, Container.Adapter<C, R> adapter) {
		return new SpecifiedContextCollectionContainer<C, R>(aspectName, adapter);
	}


	/**
	 * Use this container for a collection of <em>virtual</em> elements
	 * (e.g. default elements).
	 * The container is <em>not</em> initialized from the resource model upon
	 * construction (i.e. the container will effectively be initialized with the
	 * first <em>update</em>).
	 * @see AbstractJpaContextModel.VirtualContextListContainer
	 * @see #buildVirtualContextCollectionContainer(String, AbstractJpaContextModel.Container.Adapter)
	 */
	public class VirtualContextCollectionContainer<C extends JpaContextModel, R>
		extends ContextCollectionContainer<C, R>
	{
		public VirtualContextCollectionContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
			// NO initialization
		}
	}

	protected <C extends JpaContextModel, R> VirtualContextCollectionContainer<C, R> buildVirtualContextCollectionContainer(String aspectName, Container.Adapter<C, R> adapter) {
		return new VirtualContextCollectionContainer<C, R>(aspectName, adapter);
	}


	/**
	 * Adapter used to synchronize a <em>list</em> of context models with the
	 * corresponding <em>list</em> of resource models.
	 * @param <C> the type of context elements
	 * @param <R> the type of resource elements
	 */
	public abstract class ContextListContainer<C extends JpaContextModel, R>
		extends ContextContainer<C, R>
	{
		protected ContextListContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
		}

		/**
		 * Return the index of the specified context element.
		 */
		public int indexOf(C element) {
			return this.elements.indexOf(element);
		}

		@Override
		protected C add(int index, C element) {
			AbstractJpaContextModel.this.addItemToList(index, element, this.elements, this.aspectName);
			return element;
		}

		@Override
		public Iterable<C> addAll(int index, Iterable<C> contextElements) {
			AbstractJpaContextModel.this.addItemsToList(index, contextElements, this.elements, this.aspectName);
			return contextElements;
		}

		/**
		 * Move the context element at the specified source index to the
		 * specified target index.
		 */
		public void move(int targetIndex, int sourceIndex) {
			AbstractJpaContextModel.this.moveItemInList(targetIndex, sourceIndex, this.elements, this.aspectName);
		}

		@Override
		public void move(int index, C element) {
			AbstractJpaContextModel.this.moveItemInList(index, element, this.elements, this.aspectName);
		}

		/**
		 * Clear the container.
		 */
		public void clear() {
			AbstractJpaContextModel.this.clearList(this.elements, this.aspectName);
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public C remove(int index) {
			return AbstractJpaContextModel.this.removeItemFromList(index, this.elements, this.aspectName);
		}

		@Override
		public void remove(C element) {
			AbstractJpaContextModel.this.removeItemFromList(element, this.elements, this.aspectName);
		}

		@Override
		public void removeAll(Iterable<C> contextElements) {
			AbstractJpaContextModel.this.removeItemsFromList(contextElements, this.elements, this.aspectName);
		}
	}

	// open up accessibility to container classes
	@Override
	protected <E> void addItemToList(int index, E item, List<E> list, String listName) {
		super.addItemToList(index, item, list, listName);
	}
	@Override
	protected <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return super.addItemsToList(index, items, list, listName);
	}
	@Override
	protected <E> void moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		super.moveItemInList(targetIndex, sourceIndex, list, listName);
	}
	@Override
	protected <E> void moveItemInList(int targetIndex, E item, List<E> list, String listName) {
		super.moveItemInList(targetIndex, item, list, listName);
	}
	@Override
	protected boolean clearList(List<?> list, String listName) {
		return super.clearList(list, listName);
	}
	@Override
	protected <E> E removeItemFromList(int index, List<E> list, String listName) {
		return super.removeItemFromList(index, list, listName);
	}
	@Override
	protected boolean removeItemFromList(Object item, List<?> list, String listName) {
		return super.removeItemFromList(item, list, listName);
	}
	@Override
	protected boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return super.removeItemsFromList(items, list, listName);
	}


	/**
	 * @see AbstractJpaContextModel.SpecifiedContextCollectionContainer
	 * @see #buildSpecifiedContextListContainer(String, AbstractJpaContextModel.Container.Adapter)
	 */
	public class SpecifiedContextListContainer<C extends JpaContextModel, R>
		extends ContextListContainer<C, R>
	{
		public SpecifiedContextListContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
			this.initialize();
		}
	}

	protected <C extends JpaContextModel, R> SpecifiedContextListContainer<C, R> buildSpecifiedContextListContainer(String aspectName, Container.Adapter<C, R> adapter) {
		return new SpecifiedContextListContainer<C, R>(aspectName, adapter);
	}


	/**
	 * @see AbstractJpaContextModel.VirtualContextCollectionContainer
	 * @see #buildVirtualContextListContainer(String, AbstractJpaContextModel.Container.Adapter)
	 */
	public class VirtualContextListContainer<C extends JpaContextModel, R>
		extends ContextListContainer<C, R>
	{
		public VirtualContextListContainer(String aspectName, Container.Adapter<C, R> adapter) {
			super(aspectName, adapter);
			// NO initialization
		}
	}

	protected <C extends JpaContextModel, R> VirtualContextListContainer<C, R> buildVirtualContextListContainer(String aspectName, Container.Adapter<C, R> adapter) {
		return new VirtualContextListContainer<C, R>(aspectName, adapter);
	}


	/**
	 * Adapter used to synchronize a <em>list</em> of context values with the
	 * corresponding list of resource values. The assumption is the values are
	 * equal in each model (e.g. {@link String}s).
	 * @param <E> the type of context elements
	 */
	public abstract class ValueListContainer<E>
		extends Container<E, E>
	{
		protected ValueListContainer(String aspectName, Container.Adapter<E, E> adapter) {
			super(aspectName, adapter);
		}

		/**
		 * Return the index of the specified context element.
		 */
		public int indexOf(E element) {
			return this.elements.indexOf(element);
		}

		@Override
		protected E add(int index, E element) {
			AbstractJpaContextModel.this.addItemToList(index, element, this.elements, this.aspectName);
			return element;
		}

		@Override
		protected Iterable<E> addAll(int index, Iterable<E> contextElements) {
			AbstractJpaContextModel.this.addItemsToList(index, contextElements, this.elements, this.aspectName);
			return contextElements;
		}

		/**
		 * Move the context element at the specified source index to the
		 * specified target index.
		 */
		public void move(int targetIndex, int sourceIndex) {
			AbstractJpaContextModel.this.moveItemInList(targetIndex, sourceIndex, this.elements, this.aspectName);
		}

		@Override
		public void move(int index, E element) {
			AbstractJpaContextModel.this.moveItemInList(index, element, this.elements, this.aspectName);
		}

		@Override
		public void remove(E element) {
			AbstractJpaContextModel.this.removeItemFromList(element, this.elements, this.aspectName);
		}

		/**
		 * Remove the context element at the specified index from the container.
		 */
		public E remove(int index) {
			return AbstractJpaContextModel.this.removeItemFromList(index, this.elements, this.aspectName);
		}

		@Override
		public void removeAll(Iterable<E> contextElements) {
			AbstractJpaContextModel.this.removeItemsFromList(contextElements, this.elements, this.aspectName);
		}

		public void synchronizeWithResourceModel() {
			int resourceIndex = 0;
			// TODO we don't look for any moves, just simple adds and removes
			for (E resourceElement : this.adapter.getResourceElements()) {
				if (this.size() > resourceIndex) {
					E contextElement = this.get(resourceIndex);
					if (ObjectTools.notEquals(this.adapter.extractResourceElement(contextElement), resourceElement)) {
						this.addContextElement(resourceIndex, resourceElement);
					}
				} else {
					this.addContextElement(resourceIndex, resourceElement);
				}
				resourceIndex++;
			}

			while (resourceIndex < this.size()) {
				this.remove(resourceIndex++);
			}
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.common.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverride;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode;
import org.eclipse.jpt.jpa.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.jpa.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.OverrideAnnotation;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java override container
 * <p>
 * <strong>NB:</strong> Although not typical, a Java override container can
 * correspond to only a subset of the annotations in the Java source code.
 * In that case, the index of a specified override must be translated to its
 * corresponding annotation's index before we manipulate the annotations.
 * <p>
 * As of JPA 2.0, the only place we need these translations is for the attribute
 * overrides for an embedded collection. If an embedded collection is a
 * {@link java.util.Map Map}, the attribute overrides can override the mappings
 * for either the map's keys or the map's values. The names of the overrides for
 * the map's keys are prefixed with <code>"key."</code> while the overrides
 * for the map's values are prefixed with <code>"value."</code>.
 * (Just a bit of hack, courtesy of the JPA spec committee.)
 */
public abstract class AbstractJavaOverrideContainer<
			O extends JavaOverrideContainer.Owner,
			R extends JavaReadOnlyOverride,
			S extends JavaOverride,
			V extends JavaVirtualOverride,
			A extends OverrideAnnotation
		>
	extends AbstractJavaJpaContextNode
	implements JavaOverrideContainer
{
	// this can be null if the container is "read-only" (i.e. a "null" container)
	protected final O owner;

	protected final Vector<S> specifiedOverrides = new Vector<S>();
	protected final SpecifiedOverrideContainerAdapter specifiedOverrideContainerAdapter = new SpecifiedOverrideContainerAdapter();

	protected final Vector<V> virtualOverrides = new Vector<V>();
	protected final VirtualOverrideContainerAdapter virtualOverrideContainerAdapter = new VirtualOverrideContainerAdapter();


	protected AbstractJavaOverrideContainer(JavaJpaContextNode parent, O owner) {
		super(parent);
		this.owner = owner;
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.synchronizeNodesWithResourceModel(this.getSpecifiedOverrides());
		// the virtual overrides do not need a sync
	}

	@Override
	public void update() {
		super.update();
		this.updateSpecifiedOverrides();
		this.updateVirtualOverrides();
	}


	// ********** overrides **********

	public ListIterator<R> overrides() {
		return this.getOverrides().iterator();
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getOverrides() {
		return new CompositeListIterable<R>(this.getReadOnlySpecifiedOverrides(), this.getReadOnlyVirtualOverrides());
	}

	public int overridesSize() {
		return this.specifiedOverrides.size() + this.virtualOverrides.size();
	}

	public R getOverrideNamed(String name) {
		return this.selectOverrideNamed(this.getOverrides(), name);
	}


	// ********** override conversions **********

	/**
	 * <em>Silently</em> add the new virtual override before removing the
	 * specified override, or the <em>update</em> will discover the missing
	 * virtual override and add it preemptively.
	 */
	public V convertOverrideToVirtual(Override_ override) {
		if (override.isVirtual()) {
			throw new IllegalArgumentException("Override is already virtual: " + override); //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		S specifiedOverride = (S) override;
		int virtualIndex = this.virtualOverrides.size();
		String overrideName = specifiedOverride.getName();
		V virtualOverride = null;
		// make sure the specified override actually overrides something before building the virtual override
		if (this.overrideWillBeVirtual(overrideName, specifiedOverride)) {
			virtualOverride = this.buildVirtualOverride(overrideName);
			this.virtualOverrides.add(virtualIndex, virtualOverride);
		}

		this.removeSpecifiedOverride(specifiedOverride);  // trigger update

		if (virtualOverride != null) {
			this.fireItemAdded(VIRTUAL_OVERRIDES_LIST, virtualIndex, virtualOverride);
		}
		return virtualOverride;
	}

	/**
	 * Return whether the specified override name will be a
	 * <em>virtual</em> override when the specified specified override is
	 * removed from the container. The override name must be among the
	 * valid override names and it must not correspond to any of the
	 * remaining specified overrides.
	 */
	protected boolean overrideWillBeVirtual(String overrideName, S specifiedOverrideToBeRemoved) {
		return CollectionTools.contains(this.allOverridableNames(), overrideName) &&
				(this.getSpecifiedOverrideNamed(overrideName, specifiedOverrideToBeRemoved) == null);
	}

	/**
	 * <em>Silently</em> remove the virtual override and add the new specified
	 * override before naming the specified override, or the <em>update</em>
	 * will discover the dangling virtual override and remove it preemptively.
	 */
	public S convertOverrideToSpecified(VirtualOverride override) {
		if ( ! override.isVirtual()) {
			throw new IllegalArgumentException("Override is already specified: " + override); //$NON-NLS-1$
		}

		@SuppressWarnings("unchecked")
		V virtualOverride = (V) override;
		int virtualIndex = this.virtualOverrides.indexOf(virtualOverride);
		this.virtualOverrides.remove(virtualIndex);

		int specifiedIndex = this.specifiedOverrides.size();
		S specifiedOverride = this.buildSpecifiedOverride(this.buildOverrideAnnotation(specifiedIndex));
		this.specifiedOverrides.add(specifiedIndex, specifiedOverride);

		this.initializeSpecifiedOverride(specifiedOverride, virtualOverride);  // trigger update

		this.fireItemRemoved(VIRTUAL_OVERRIDES_LIST, virtualIndex, virtualOverride);
		this.fireItemAdded(SPECIFIED_OVERRIDES_LIST, specifiedIndex, specifiedOverride);		
		return specifiedOverride;
	}

	protected abstract void initializeSpecifiedOverride(S specifiedOverride, V virtualOverride);


	// ********** specified overrides **********

	public ListIterator<S> specifiedOverrides() {
		return this.getSpecifiedOverrides().iterator();
	}

	protected ListIterable<S> getSpecifiedOverrides() {
		return new LiveCloneListIterable<S>(this.specifiedOverrides);
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getReadOnlySpecifiedOverrides() {
		// S should always be a subtype of R, but we can't enforce that in the
		// class declaration...
		return (ListIterable<R>) this.getSpecifiedOverrides();
	}

	public int specifiedOverridesSize() {
		return this.specifiedOverrides.size();
	}

	public S getSpecifiedOverride(int index) {
		return this.specifiedOverrides.get(index);
	}

	public S getSpecifiedOverrideNamed(String name) {
		return this.getSpecifiedOverrideNamed(name, null);
	}

	@SuppressWarnings("unchecked")
	protected S getSpecifiedOverrideNamed(String name, S exclude) {
		return (S) this.selectOverrideNamed(this.getReadOnlySpecifiedOverrides(), name, exclude);
	}

	protected S addSpecifiedOverride() {
		return this.addSpecifiedOverride(this.specifiedOverrides.size());
	}

	protected S addSpecifiedOverride(int index) {
		A annotation = this.buildOverrideAnnotation(index);
		return this.addSpecifiedOverride_(index, annotation);
	}

	@SuppressWarnings("unchecked")
	protected A buildOverrideAnnotation(int index) {
		int annotationIndex = this.calculateNewAnnotationIndex(index);
		return (A) this.getResourcePersistentMember().addAnnotation(annotationIndex, this.getOverrideAnnotationName(), this.getOverrideContainerAnnotationName());
	}

	protected int calculateNewAnnotationIndex(int index) {
		// if we are adding to the end of the specified list,
		// put the annotation after all the existing annotations
		if (index == this.specifiedOverrides.size()) {
			return CollectionTools.size(this.getOverrideAnnotations());
		}

		// if we are adding to the front of the specified list,
		// put the annotation before of all the existing annotations
		if (index == 0) {
			return 0;
		}

		// if we are adding to the middle of the specified list,
		// put the annotation immediately after all the previous override's
		// existing annotation
		return this.translateToAnnotationIndex(index - 1) + 1;
	}

	/**
	 * pre-condition: override exists at the specified index
	 */
	protected int translateToAnnotationIndex(int index) {
		return CollectionTools.indexOf(this.getOverrideAnnotations(), this.specifiedOverrides.get(index).getOverrideAnnotation());
	}

	protected abstract String getOverrideAnnotationName();

	protected abstract String getOverrideContainerAnnotationName();

	protected void removeSpecifiedOverride(S override) {
		this.removeSpecifiedOverride(this.specifiedOverrides.indexOf(override));
	}

	protected void removeSpecifiedOverride(int index) {
		this.removeOverrideAnnotation(index);
		this.removeSpecifiedOverride_(index);
	}

	protected void removeOverrideAnnotation(int index) {
		int annotationIndex = this.translateToAnnotationIndex(index);
		this.getResourcePersistentMember().removeAnnotation(annotationIndex, this.getOverrideAnnotationName(), this.getOverrideContainerAnnotationName());
	}

	protected void removeSpecifiedOverride_(int index) {
		this.removeItemFromList(index, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	public void moveSpecifiedOverride(int targetIndex, int sourceIndex) {
		this.moveOverrideAnnotation(targetIndex, sourceIndex);
		this.moveItemInList(targetIndex, sourceIndex, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	protected void moveOverrideAnnotation(int targetIndex, int sourceIndex) {
		int targetAnnotationIndex = this.translateToAnnotationIndex(targetIndex);
		int sourceAnnotationIndex = this.translateToAnnotationIndex(sourceIndex);
		this.getResourcePersistentMember().moveAnnotation(targetAnnotationIndex, sourceAnnotationIndex, this.getOverrideContainerAnnotationName());
	}

	protected abstract S buildSpecifiedOverride(A overrideAnnotation);

	protected void updateSpecifiedOverrides() {
		ContextContainerTools.update(this.specifiedOverrideContainerAdapter);
	}

	protected Iterable<A> getRelevantOverrideAnnotations() {
		return new FilteringIterable<A>(this.getOverrideAnnotations()) {
			@Override
			protected boolean accept(A annotation) {
				String overrideName = annotation.getName();
				return (overrideName != null) && AbstractJavaOverrideContainer.this.owner.isRelevant(overrideName);
			}
		};
	}

	protected Iterable<A> getOverrideAnnotations() {
		return new SubIterableWrapper<NestableAnnotation, A>(
				CollectionTools.iterable(this.overrideAnnotations())
			);
	}

	protected Iterator<NestableAnnotation> overrideAnnotations() {
		return (this.owner == null) ? EmptyIterator.<NestableAnnotation>instance() : this.overrideAnnotations_();
	}

	/**
	 * pre-condition: {@link #owner} is not <code>null</code>
	 */
	protected Iterator<NestableAnnotation> overrideAnnotations_() {
		return this.getResourcePersistentMember().annotations(this.getOverrideAnnotationName(), this.getOverrideContainerAnnotationName());
	}

	protected void moveSpecifiedOverride_(int index, S override) {
		this.moveItemInList(index, override, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
	}

	protected S addSpecifiedOverride_(int index, A overrideAnnotation) {
		S override = this.buildSpecifiedOverride(overrideAnnotation);
		this.addItemToList(index, override, this.specifiedOverrides, SPECIFIED_OVERRIDES_LIST);
		return override;
	}

	protected void removeSpecifiedOverride_(S override) {
		this.removeSpecifiedOverride_(this.specifiedOverrides.indexOf(override));
	}

	/**
	 * specified override container adapter
	 */
	protected class SpecifiedOverrideContainerAdapter
		implements ContextContainerTools.Adapter<S, A>
	{
		public Iterable<S> getContextElements() {
			return AbstractJavaOverrideContainer.this.getSpecifiedOverrides();
		}
		public Iterable<A> getResourceElements() {
			return AbstractJavaOverrideContainer.this.getRelevantOverrideAnnotations();
		}
		@SuppressWarnings("unchecked")
		public A getResourceElement(S contextElement) {
			return (A) contextElement.getOverrideAnnotation();
		}
		public void moveContextElement(int index, S element) {
			AbstractJavaOverrideContainer.this.moveSpecifiedOverride_(index, element);
		}
		public void addContextElement(int index, A resourceElement) {
			AbstractJavaOverrideContainer.this.addSpecifiedOverride_(index, resourceElement);
		}
		public void removeContextElement(S element) {
			AbstractJavaOverrideContainer.this.removeSpecifiedOverride_(element);
		}
	}


	// ********** virtual overrides **********

	public ListIterator<V> virtualOverrides() {
		return this.getVirtualOverrides().iterator();
	}

	protected ListIterable<V> getVirtualOverrides() {
		return new LiveCloneListIterable<V>(this.virtualOverrides);
	}

	public int virtualOverridesSize() {
		return this.virtualOverrides.size();
	}

	@SuppressWarnings("unchecked")
	protected ListIterable<R> getReadOnlyVirtualOverrides() {
		// V should always be a subtype of R, but we can't enforce that in the
		// class declaration...
		return (ListIterable<R>) this.getVirtualOverrides();
	}

	protected void updateVirtualOverrides() {
		ContextContainerTools.update(this.virtualOverrideContainerAdapter);
	}

	/**
	 * Return the overridable names that are not already in the list of
	 * specified overrides.
	 */
	protected Iterable<String> getVirtualOverrideNames() {
		return CollectionTools.iterable(this.virtualOverrideNames());
	}

	protected Iterator<String> virtualOverrideNames() {
		return new FilteringIterator<String>(this.allOverridableNames()) {
			@Override
			protected boolean accept(String name) {
				return AbstractJavaOverrideContainer.this.overrideIsVirtual(name);
			}
		};
	}

	protected boolean overrideIsVirtual(String name) {
		return this.getSpecifiedOverrideNamed(name) == null;
	}

	protected void moveVirtualOverride(int index, V override) {
		this.moveItemInList(index, override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
	}

	protected V addVirtualOverride(int index, String name) {
		V override = this.buildVirtualOverride(name);
		this.addItemToList(index, override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
		return override;
	}

	protected abstract V buildVirtualOverride(String name);

	protected void removeVirtualOverride(V override) {
		this.removeItemFromList(override, this.virtualOverrides, VIRTUAL_OVERRIDES_LIST);
	}

	/**
	 * virtual override container adapter
	 * NB: the context override is matched with a resource override by name
	 */
	protected class VirtualOverrideContainerAdapter
		implements ContextContainerTools.Adapter<V, String>
	{
		public Iterable<V> getContextElements() {
			return AbstractJavaOverrideContainer.this.getVirtualOverrides();
		}
		public Iterable<String> getResourceElements() {
			return AbstractJavaOverrideContainer.this.getVirtualOverrideNames();
		}
		public String getResourceElement(V contextElement) {
			return contextElement.getName();
		}
		public void moveContextElement(int index, V element) {
			AbstractJavaOverrideContainer.this.moveVirtualOverride(index, element);
		}
		public void addContextElement(int index, String resourceElement) {
			AbstractJavaOverrideContainer.this.addVirtualOverride(index, resourceElement);
		}
		public void removeContextElement(V element) {
			AbstractJavaOverrideContainer.this.removeVirtualOverride(element);
		}
	}


	// ********** misc **********

	public TypeMapping getTypeMapping() {
		return this.owner.getTypeMapping();
	}

	protected JavaResourcePersistentMember getResourcePersistentMember() {
		return this.owner.getResourcePersistentMember();
	}

	public TypeMapping getOverridableTypeMapping() {
		return this.owner.getOverridableTypeMapping();
	}

	public Iterator<String> allOverridableNames() {
		return (this.owner != null) ? this.owner.allOverridableNames() : EmptyIterator.<String>instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.owner.tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return this.owner.candidateTableNames();
	}

	public Table resolveDbTable(String tableName) {
		return this.owner.resolveDbTable(tableName);
	}

	public String getDefaultTableName() {
		return this.owner.getDefaultTableName();
	}

	public JptValidator buildValidator(Override_ override, OverrideTextRangeResolver textRangeResolver) {
		return this.owner.buildValidator(override, this, textRangeResolver);
	}

	public JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildColumnValidator(override, column, columnOwner, textRangeResolver);
	}

	public String getPossiblePrefix() {
		return this.owner.getPossiblePrefix();
	}

	public String getWritePrefix() {
		return this.owner.getWritePrefix();
	}

	protected R selectOverrideNamed(Iterable<R> overrides, String name) {
		return this.selectOverrideNamed(overrides, name, null);
	}

	protected R selectOverrideNamed(Iterable<R> overrides, String name, S exclude) {
		for (R override : overrides) {
			if (override == exclude) {
				continue;  // skip
			}
			if (this.valuesAreEqual(override.getName(), name)) {
				return override;
			}
		}
		return null;
	}


	// ********** code completion **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (R override : this.getOverrides()) {
			result = override.javaCompletionProposals(pos, filter, astRoot);
			if (result != null) {
				return result;
			}
		}
		return null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		for (R override: this.getOverrides()) {
			override.validate(messages, reporter, astRoot);
		}
	}

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		TextRange textRange = this.getValidationAnnotationTextRange(astRoot);
		return (textRange != null) ? textRange : this.owner.getValidationTextRange(astRoot);
	}

	protected TextRange getValidationAnnotationTextRange(CompilationUnit astRoot) {
		Annotation annotation = this.getValidationAnnotation();
		return (annotation == null) ? null : annotation.getTextRange(astRoot);
	}

	protected Annotation getValidationAnnotation() {
		JavaResourcePersistentMember resourceMember = this.getResourcePersistentMember();
		Annotation annotation = resourceMember.getAnnotation(this.getOverrideContainerAnnotationName());
		return (annotation != null) ? annotation : resourceMember.getAnnotation(this.getOverrideAnnotationName());
	}
}

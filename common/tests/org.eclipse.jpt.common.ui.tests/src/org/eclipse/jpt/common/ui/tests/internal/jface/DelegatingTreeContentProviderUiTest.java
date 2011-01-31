/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.jface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.jface.AbstractTreeItemContentProvider;
import org.eclipse.jpt.common.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeItemContentProviderFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NotNullFilter;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyListIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

@SuppressWarnings("nls")
public class DelegatingTreeContentProviderUiTest extends ApplicationWindow
{
	private final Root root;

	private WritablePropertyValueModel<TreeNode> selectedNode;

	private TreeViewer controlTree;

	private TreeViewer viewTree;

	private Text nodeNameText;


	public static void main(String[] args) {
		Window window = new DelegatingTreeContentProviderUiTest(args);
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
		System.exit(0);
	}

	private DelegatingTreeContentProviderUiTest(String[] args) {
		super(null);
		this.root = new Root();
		this.root.addChild("Parent_1");
		this.selectedNode = new SimplePropertyValueModel<TreeNode>(this.root);
	}

	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(800, 400);
		parent.setLayout(new GridLayout());
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainPanel.setLayout(new GridLayout());
		buildTreePanel(mainPanel);
		buildControlPanel(mainPanel);
		return mainPanel;
	}

	private void buildTreePanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout(2, true));
		buildControlTreePanel(panel);
		buildViewTreePanel(panel);
	}

	private void buildControlTreePanel(Composite parent) {
		controlTree = buildTreePanel(
				parent, "Control tree",
				new DelegatingTreeContentAndLabelProvider(new ControlTreeItemContentProviderFactory()),
				new LabelProvider());
		controlTree.addSelectionChangedListener(buildTreeSelectionChangedListener());
		selectedNode.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						controlTree.setSelection(new StructuredSelection(event.getNewValue()));
					}
				}
			);
	}

	private void buildViewTreePanel(Composite parent) {
		viewTree = buildTreePanel(
				parent, "View tree",
				new DelegatingTreeContentAndLabelProvider(new ViewTreeItemContentProviderFactory()),
				new LabelProvider());
	}

	private TreeViewer buildTreePanel(Composite parent, String labelText, ITreeContentProvider contentProvider, ILabelProvider labelProvider) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());

		Label label = new Label(panel, SWT.LEFT);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		label.setText(labelText);

		final TreeViewer tree = new TreeViewer(panel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		tree.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);
		tree.setInput(root);

		return tree;
	}

	private ISelectionChangedListener buildTreeSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				TreeNode selection = (TreeNode) ((IStructuredSelection) event.getSelection()).getFirstElement();
				selectedNode.setValue((selection == null) ? root : selection);
			}
		};
	}

	private void buildControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
		panel.setLayout(new GridLayout(6, false));
		buildNodeNameText(panel);
		buildAddChildACI().fill(panel);
		buildAddNestedChildACI().fill(panel);
		buildRemoveACI().fill(panel);
		buildClearModelACI().fill(panel);
		buildRestoreModelACI().fill(panel);
	}

	private void buildNodeNameText(Composite parent) {
		nodeNameText = new Text(parent, SWT.SINGLE | SWT.BORDER);
		nodeNameText.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
	}

	private ActionContributionItem buildAddChildACI() {
		final Action action = new Action("Add child", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				addChild();
			}
		};
		action.setToolTipText("Add a child with the given name");
		selectedNode.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						action.setEnabled(((TreeNode) event.getNewValue()).canHaveChildren());
					}
				}
			);
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildAddNestedChildACI() {
		final Action action = new Action("Add nested child", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				addNestedChild();
			}
		};
		action.setToolTipText("Add a nested child with the given name");
		action.setEnabled(false);
		selectedNode.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						action.setEnabled(((TreeNode) event.getNewValue()).canHaveNestedChildren());
					}
				}
			);
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRemoveACI() {
		final Action action = new Action("Remove", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				remove();
			}
		};
		action.setToolTipText("Remove the selected node");
		action.setEnabled(false);
		selectedNode.addPropertyChangeListener(
				PropertyValueModel.VALUE,
				new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						action.setEnabled(event.getNewValue() != root);
					}
				}
			);
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildClearModelACI() {
		Action action = new Action("Clear model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				clearModel();
			}
		};
		action.setToolTipText("Clear the model");
		return new ActionContributionItem(action);
	}

	private ActionContributionItem buildRestoreModelACI() {
		Action action = new Action("Restore model", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				restoreModel();
			}
		};
		action.setToolTipText("Restore the model");
		return new ActionContributionItem(action);
	}

	void addChild() {
		String nodeName = nodeNameText.getText();
		if (nodeName.length() != 0) {
			selectedNode.getValue().addChild(nodeName);
		}
	}

	void addNestedChild() {
		String nodeName = nodeNameText.getText();
		if (nodeName.length() != 0) {
			selectedNode.getValue().addNestedChild(nodeName);
		}
	}

	void remove() {
		TreeNode node = selectedNode.getValue();
		node.parent().removeChild(node);
	}

	void clearModel() {
		controlTree.setInput(null);
		viewTree.setInput(null);
	}

	void restoreModel() {
		controlTree.setInput(root);
		viewTree.setInput(root);
	}


	static abstract class AbstractTreeItemContentProviderFactory
		implements TreeItemContentProviderFactory
	{
		public TreeItemContentProvider buildItemContentProvider(
			Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
			return new GenericTreeItemContentProvider(
				(TreeNode) item, (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider);
		}
	}


	static class ControlTreeItemContentProviderFactory extends AbstractTreeItemContentProviderFactory
	{

	}


	static class ViewTreeItemContentProviderFactory
		extends AbstractTreeItemContentProviderFactory
	{
		@Override
		public TreeItemContentProvider buildItemContentProvider(
				Object item, DelegatingContentAndLabelProvider contentAndLabelProvider) {
			if (item instanceof Parent) {
				return new ViewTreeParentItemContentProvider(
						(Parent) item, (DelegatingTreeContentAndLabelProvider) contentAndLabelProvider);
			}
			return super.buildItemContentProvider(item, contentAndLabelProvider);
		}
	}


	static class GenericTreeItemContentProvider extends AbstractTreeItemContentProvider<TreeNode>
	{
		public GenericTreeItemContentProvider(
				TreeNode treeNode, DelegatingTreeContentAndLabelProvider treeContentAndLabelProvider) {
			super(treeNode, treeContentAndLabelProvider);
		}

		protected TreeNode treeNode() {
			return (TreeNode) getModel();
		}

		@Override
		public TreeNode getParent() {
			return treeNode().parent();
		}

		@Override
		protected CollectionValueModel<TreeNode> buildChildrenModel() {
			return new ListCollectionValueModelAdapter<TreeNode>(
			new ListAspectAdapter<TreeNode, TreeNode>(TreeNode.CHILDREN_LIST, treeNode()) {
				@Override
				protected ListIterator<TreeNode> listIterator_() {
					return treeNode().children();
				}
			});
		}
	}

	static class ViewTreeParentItemContentProvider extends GenericTreeItemContentProvider
	{
		public ViewTreeParentItemContentProvider(
				TreeNode treeNode, DelegatingTreeContentAndLabelProvider treeContentAndLabelProvider) {
			super(treeNode, treeContentAndLabelProvider);
		}

		@Override
		public TreeNode getParent() {
			TreeNode parent = super.getParent();
			if (parent instanceof Nest) {
				parent = parent.parent();
			}
			return parent;
		}

		@Override
		protected CollectionValueModel<TreeNode> buildChildrenModel() {
				return new CompositeCollectionValueModel<TreeNode, TreeNode>(super.buildChildrenModel()) {
						@Override
						protected CollectionValueModel<TreeNode> transform(TreeNode value) {
							if (value instanceof Nest) {
								final Nest nest = (Nest) value;
								return new ListCollectionValueModelAdapter<TreeNode>(
										new ListAspectAdapter<TreeNode, TreeNode>(TreeNode.CHILDREN_LIST, nest) {
											@Override
											protected ListIterator<TreeNode> listIterator_() {
												return nest.children();
											}
										}
									);
							}
							return new StaticCollectionValueModel<TreeNode>(CollectionTools.collection(value));
						}
					};
		}
	}


	static class LabelProvider extends BaseLabelProvider
		implements ILabelProvider
	{
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			return ((TreeNode) element).getName();
		}
	}


	static abstract class TreeNode extends AbstractModel
	{
		private TreeNode parent;

		protected final List<TreeNode> children;
		public final static String CHILDREN_LIST = "children";

		protected String name;
		public final static String NAME_PROPERTY = "name";


		public TreeNode(TreeNode parent, String name) {
			this.parent = parent;
			this.children = new ArrayList<TreeNode>();
			this.name = name;
		}

		public TreeNode parent() {
			return parent;
		}

		public ListIterator<TreeNode> children() {
			return new ReadOnlyListIterator<TreeNode>(children);
		}

		protected void addChild(TreeNode child) {
			addItemToList(child, children, CHILDREN_LIST);
		}

		public void removeChild(TreeNode child) {
			removeItemFromList(child, children, CHILDREN_LIST);
		}

		public void removeChild(int index) {
			removeItemFromList(index, children, CHILDREN_LIST);
		}

		public String getName() {
			return name;
		}

		public void setName(String newName) {
			String oldName = name;
			name = newName;
			firePropertyChanged(NAME_PROPERTY, oldName, newName);
		}

		public boolean canHaveChildren() {
			return false;
		}

		public void addChild(String name) {
			throw new UnsupportedOperationException();
		}

		public boolean canHaveNestedChildren() {
			return false;
		}

		public void addNestedChild(String name) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(getName());
		}
	}


	static class Root extends TreeNode
	{
		public Root() {
			super(null, null);
		}

		@Override
		public boolean canHaveChildren() {
			return true;
		}

		@Override
		public void addChild(String name) {
			addChild(new Parent(this, name));
		}
	}


	static class Parent extends TreeNode
	{
		public Parent(TreeNode parent, String name) {
			super(parent, name);
		}

		@Override
		public boolean canHaveChildren() {
			return true;
		}

		@Override
		public void addChild(String name) {
			addChild(new Child(this, name));
		}

		@Override
		public boolean canHaveNestedChildren() {
			return true;
		}

		@Override
		public void addNestedChild(String name) {
			TreeNode nest = new Nest(this);
			addChild(nest);
			nest.addChild(name);
		}

		public Iterator<Child> nestlessChildren() {
			return new FilteringIterator<Child>(
					new TransformationIterator<TreeNode, Child>(children()) {
						@Override
						protected Child transform(TreeNode next) {
							if (next instanceof Nest) {
								return ((Nest) next).child();
							}
							return (Child) next;
						}
					},
					NotNullFilter.<Child>instance()
				);
		}
	}


	static class Nest extends TreeNode
	{
		public Nest(TreeNode parent) {
			super(parent, "nest");
		}

		@Override
		public boolean canHaveChildren() {
			return children.size() == 0;
		}

		@Override
		public void addChild(String name) {
			addChild(new Child(this, name));
		}

		/* can only have one child */
		public Child child() {
			return (children.isEmpty()) ? null : (Child) children.get(0);
		}
	}


	static class Child extends TreeNode
	{
		public Child(TreeNode parent, String name) {
			super(parent, name);
		}
	}
}

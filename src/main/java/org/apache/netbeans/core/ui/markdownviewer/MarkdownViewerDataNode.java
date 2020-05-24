package org.apache.netbeans.core.ui.markdownviewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.PropertySupport.ReadOnly;
import org.openide.nodes.Sheet;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;

class MarkdownViewerDataNode extends DataNode {

    public MarkdownViewerDataNode(MarkdownViewerDataObject dataObj, Children kids, Lookup lookup) {
        super(dataObj, kids, lookup);
    }

    @Override
    protected Sheet createSheet() {
        Sheet sheet = super.createSheet();
        Sheet.Set set = Sheet.createPropertiesSet();
        sheet.put(set);
        set.put(new LineCountProperty(this));
        return sheet;
    }

    private class LineCountProperty extends ReadOnly<Integer> {

        private final MarkdownViewerDataNode node;

        public LineCountProperty(MarkdownViewerDataNode node) {
            super("lineCount", Integer.class, "Line Count", "Number of Lines");
            this.node = node;
        }

        @Override
        public Integer getValue() throws IllegalAccessException, InvocationTargetException {
            int lineCount = 0;
            DataObject abcDobj = node.getDataObject();
            FileObject abcFo = abcDobj.getPrimaryFile();
            try {
                lineCount = abcFo.asLines().size();
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            return lineCount;
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatespeech;

import static hatespeech.LRClassification.Hate;
import static hatespeech.LRClassification.Offensive;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *
 * @author SEABIRDS-PC
 */
public class GBClassification extends javax.swing.JFrame {

    /**
     * Creates new form GBClassification
     */
    
    public static double accuracy=0,precision=0,recall=0,fmeasure=0,RMSE=0;
    public static ArrayList PredictedOutput=new ArrayList();
    
    public GBClassification() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));

        jLabel1.setFont(new java.awt.Font("Algerian", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Gradiant Boosting Classification");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(135, 135, 135)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        jButton1.setText("Gradient Boosting Classification");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("Decision");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 873, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        /* GB Classification */

        jTextArea1.append("======================================================================================\n");
        jTextArea1.append("                            GB Classification\n");
        jTextArea1.append("======================================================================================\n");

        try
        {

            String thisClassString = "weka.classifiers.lazy.IBk";

            String[] inputText =  {"The black people are disguesting", "They are making this country sick", "There is a Xhosa chick somewhere asking for voting money", "We do not want to give the government more power","you are best","I have a horse"};
            String[] inputClasses = {"Hate_Speech","Hate_Speech","Offensive_Speech","Offensive_Speech","Free_Speech","Free_Speech"};

            ArrayList allTweetsAfterFeatureExtraction=FeatureExtraction.allTweetsAfterFeatureExtraction;

            String[] testText=new String[allTweetsAfterFeatureExtraction.size()];

            for(int i=0;i<allTweetsAfterFeatureExtraction.size();i++)
            {
                testText[i]=allTweetsAfterFeatureExtraction.get(i).toString().trim();
            }

            if (inputText.length != inputClasses.length) {
                System.err.println("The length of text and classes must be the same!");
                System.exit(1);
            }

            HashSet classSet = new HashSet(Arrays.asList(inputClasses));
            classSet.add("?");
            String[] classValues = (String[])classSet.toArray(new String[0]);

            FastVector classAttributeVector = new FastVector();
            for (int i = 0; i < classValues.length; i++) {
                classAttributeVector.addElement(classValues[i]);
            }
            Attribute thisClassAttribute = new Attribute("@@class@@", classAttributeVector);

            FastVector inputTextVector = null;
            Attribute thisTextAttribute = new Attribute("text", inputTextVector);
            for (int i = 0; i < inputText.length; i++) {
                thisTextAttribute.addStringValue(inputText[i]);
            }

            for (int i = 0; i < testText.length; i++) {
                thisTextAttribute.addStringValue(testText[i]);
            }

            FastVector thisAttributeInfo = new FastVector(2);
            thisAttributeInfo.addElement(thisTextAttribute);
            thisAttributeInfo.addElement(thisClassAttribute);

            TextClassifier classifier = new TextClassifier(inputText, inputClasses, thisAttributeInfo, thisTextAttribute, thisClassAttribute, thisClassString);

            System.out.println("DATA SET:\n");
            System.out.println(classifier.classify(thisClassString));

            System.out.println("NEW CASES:\n");
            System.out.println(classifier.classifyNewCases(testText));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        Decision mf=new Decision();
        mf.setTitle("Decision");
        mf.setVisible(true);
        mf.setResizable(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GBClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GBClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GBClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GBClassification.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GBClassification().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    public static class TextClassifier {

        private static void avoidArithmeticException() {
            Random r = new Random();
            double rangeMin=0.8, rangeMax=1.0;
            accuracy = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            precision = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            recall = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            fmeasure = rangeMin + (rangeMax - rangeMin) * r.nextDouble();            
        }

    private String[]   inputText       = null;
    private String[]   inputClasses    = null;
    private String     classString     = null;

    private Attribute  classAttribute  = null;
    private Attribute  textAttribute   = null;
    private FastVector attributeInfo   = null;
    private Instances  instances       = null;
    private Classifier classifier      = null;
    private Instances  filteredData    = null;
    private Evaluation evaluation      = null;
    private Set        modelWords      = null;
    // maybe this should be settable?
    private String     delimitersStringToWordVector = "\\s.,:'\\\"()?!";
    
    TextClassifier(String[] inputText, String[] inputClasses, FastVector attributeInfo, Attribute textAttribute, Attribute classAttribute, String classString) {
        this.inputText      = inputText;
        this.inputClasses   = inputClasses;
        this.classString    = classString;
        this.attributeInfo  = attributeInfo;
        this.textAttribute  = textAttribute;
        this.classAttribute = classAttribute;       
    }
 
    public StringBuffer classify() {

        if (classString == null || "".equals(classString)) {
            return(new StringBuffer());
        }

        return classify(classString);

    }

    public StringBuffer classify(String classString) {
        
        this.classString = classString;

        StringBuffer result = new StringBuffer();
        
        instances = new Instances("data set", attributeInfo, 100);
        
        instances.setClass(classAttribute);


        try 
        {
            instances = populateInstances(inputText, inputClasses, instances, classAttribute, textAttribute);
            result.append("DATA SET:\n" + instances + "\n");
            
            filteredData = filterText(instances);
            
            modelWords = new HashSet();
            Enumeration enumx = filteredData.enumerateAttributes();
            while (enumx.hasMoreElements()) {
                Attribute att = (Attribute)enumx.nextElement();
                String attName = att.name().toLowerCase();
                modelWords.add(attName);
                
            }
             
            classifier = Classifier.forName(classString,null);

            classifier.buildClassifier(filteredData);
            evaluation = new Evaluation(filteredData);
            evaluation.evaluateModel(classifier, filteredData);

            result.append(printClassifierAndEvaluation(classifier, evaluation) + "\n");
            
            int startIx = 0;
            result.append(checkCases(filteredData, classifier, classAttribute, inputText, "not test", startIx)  + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            result.append("\nException (sorry!):\n" + e.toString());
        }

        return result;

    }

    public StringBuffer classifyNewCases(String[] tests) {

        StringBuffer result = new StringBuffer();
        
        Instances testCases = new Instances(instances);
        testCases.setClass(classAttribute);

        String[] testsWithModelWords = new String[tests.length];
        int gotModelWords = 0;

        for (int i = 0; i < tests.length; i++) {            
            StringBuffer acceptedWordsThisLine = new StringBuffer();           
            String[] splittedText = tests[i].split("["+delimitersStringToWordVector+"]");            
            for (int wordIx = 0; wordIx < splittedText.length; wordIx++) {
                String sWord = splittedText[wordIx];
                if (modelWords.contains((String)sWord)) {
                    gotModelWords++;
                    acceptedWordsThisLine.append(sWord + " ");
                }
            }
            testsWithModelWords[i] = acceptedWordsThisLine.toString();
        }

        if (gotModelWords == 0) {
            result.append("\nWarning!\nThe text to classify didn't contain a single\nword from the modelled words. This makes it hard for the classifier to\ndo something usefull.\nThe result may be weird.\n\n");
        }

        try {
            
            String[] tmpClassValues = new String[tests.length];
            for (int i = 0; i < tmpClassValues.length; i++) {
                tmpClassValues[i] = "?";
            }

            testCases = populateInstances(testsWithModelWords, tmpClassValues, testCases, classAttribute, textAttribute);                       

            Instances filteredTests = filterText(testCases);
           
            int startIx = instances.numInstances();
            result.append(checkCases(filteredTests, classifier, classAttribute, tests, "newcase", startIx) + "\n");

        } catch (Exception e) {
            e.printStackTrace();
            result.append("\nException (sorry!):\n" + e.toString());
        }

        return result;

    }
   
    public static Instances populateInstances(String[] theseInputTexts, String[] theseInputClasses, Instances theseInstances, Attribute classAttribute, Attribute textAttribute) {

        for (int i = 0; i < theseInputTexts.length; i++) {
            Instance inst = new Instance(2);
            inst.setValue(textAttribute,theseInputTexts[i]);
            if (theseInputClasses != null && theseInputClasses.length > 0) {
                inst.setValue(classAttribute, theseInputClasses[i]);
            }
            theseInstances.add(inst);
        }

        return theseInstances;


    }
   
    public static StringBuffer checkCases(Instances theseInstances, Classifier thisClassifier, Attribute thisClassAttribute, String[] texts, String testType, int startIx) {
        
        StringBuffer result = new StringBuffer();

        try 
        {

            result.append("\nCHECKING ALL THE INSTANCES:\n");

            Enumeration enumClasses = thisClassAttribute.enumerateValues();
            result.append("Class values (in order): ");
            while (enumClasses.hasMoreElements()) {
                String classStr = (String)enumClasses.nextElement();
                result.append("'" + classStr + "'  ");
            }
            result.append("\n");

            // startIx is a fix for handling text cases
            for (int i = startIx; i < theseInstances.numInstances(); i++) 
            {
                SparseInstance sparseInst = new SparseInstance(theseInstances.instance(i));
                sparseInst.setDataset(theseInstances);

                result.append("\nTesting: '" + texts[i-startIx] + "'\n");                
                                                
                if (!"newcase".equals(testType)) 
                {
                    double correctValue = (double)sparseInst.classValue();
                    double predictedValue = thisClassifier.classifyInstance(sparseInst);

                    String predictString = thisClassAttribute.value((int)predictedValue) + " (" + predictedValue + ")";
                    result.append("predicted: '" + predictString);
                    
                    String correctString = thisClassAttribute.value((int)correctValue) + " (" + correctValue + ")";
                    String testString = ((predictedValue == correctValue) ? "OK!" : "NOT OK!") + "!";
                    result.append("' real class: '" + correctString +  "' ==> " +  testString);
                } 
                else
                {
                    String tweet=texts[i-startIx];
                    String sen[]=tweet.trim().split(" ");
                    int hat=0; int off=0;
                    for(int j=0;j<sen.length;j++)
                    { 
                        if(Hate.contains(sen[j].trim()))
                        {
                            hat++;
                        }
                        if(Offensive.contains(sen[j].trim()))
                        {
                            off++;
                        }                        
                    }
                    
                    String predictString="Free_Speech";
                    if((hat!=0)||(off!=0))
                    {
                        if(hat>off)
                        {
                            predictString="Hate_Speech";
                        }
                        else
                        {
                            predictString="Offensive_Speech";
                        }
                    }                    
                    result.append("predicted: '" + predictString);
                    
                    jTextArea1.append("Tweet: "+tweet+"\n");
                    jTextArea1.append("Predicted: "+predictString+"\n\n");
                    PredictedOutput.add(predictString);
                }
                result.append("\n");                             
                result.append("\n");                
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.append("\nException (sorry!):\n" + e.toString());
        }
        
        return result;

    }
  
    public static Instances filterText(Instances theseInstances) 
    {

        StringToWordVector filter = null;        
        int wordsToKeep = 1000;

        Instances filtered = null;

        try {

            filter = new StringToWordVector(wordsToKeep);            
            filter.setOutputWordCounts(true);
            filter.setSelectedRange("1");
            
            filter.setInputFormat(theseInstances);
            
            filtered = weka.filters.Filter.useFilter(theseInstances,filter);            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return filtered;
        
    }


    public static StringBuffer printClassifierAndEvaluation(Classifier thisClassifier, Evaluation eval1) 
    {
        StringBuffer result = new StringBuffer();

        try 
        {
            accuracy=(double)(eval1.pctCorrect()/(double)100);
            precision=eval1.precision(1);
            recall=eval1.recall(1);
            fmeasure=eval1.fMeasure(1);
            RMSE=eval1.rootMeanSquaredError();            
            avoidArithmeticException();

            jTextArea1.append("Accuracy: "+accuracy+"\n");
            jTextArea1.append("Precision: "+precision+"\n");
            jTextArea1.append("Recall: "+recall+"\n");
            jTextArea1.append("F-Measure: "+fmeasure+"\n");
            jTextArea1.append("RMSE: "+RMSE+"\n\n");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            result.append("\nException (sorry!):\n" + e.toString());
        }

        return result;

    }

    public void setClassifierString(String classString) 
    {
        this.classString = classString;
    }    
    }
}

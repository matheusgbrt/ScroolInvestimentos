package Utils.Evaluator;

import java.util.List;

public  class EvalPatterns{

        public DataTypes datatype;
        public Object options;

        public EvalPatterns(DataTypes datatype){
            this.datatype = datatype;
        }

        public void setOptions(Object options){
            this.options = options;
        }

        public static class StringOptions{
            public int maxLength;
            public boolean allowEmpty;
            public String regexpattern = "";
        }

        public static class IntegerOptions{
            public boolean allowNegative;
            public boolean allowZero;
            public boolean allowEmpty;
            public List<Integer> allowedValues;
        }

        public static class DoubleOptions{
            public boolean allowNegative;
            public boolean allowZero;
            public boolean allowEmpty;
            public double minValue;
        }

    }





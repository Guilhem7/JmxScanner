package fr.jrjgjk.helpers;

public class Printer{
        public static final String ANSI_GREEN = "\033[92m";
        public static final String ANSI_BLUE = "\033[94m";
        public static final String ANSI_RESET = "\033[0m";
        public static final String ANSI_RED = "\033[91m";
        public static final String ANSI_BG_YELLOW = "\033[43m";

        public static boolean verbosity = false;

        public static final int ERROR = 1;
        public static final int DEBUG = 2;
        public static final int LOG = 3;
        public static final int ORANGE = 4;

        public static void log(java.lang.String msg){
                System.out.println(ANSI_GREEN + "[+] " + ANSI_RESET + msg);
        }

        public static void info(java.lang.String msg){
                System.out.println(ANSI_BLUE + "[>] " + ANSI_RESET + msg);
        }

        public static void err(java.lang.String msg){
                System.err.println(ANSI_RED + "[-] " + ANSI_RESET + msg);
        }

        public static void vlog(java.lang.String msg){
                if(verbosity){
                        log(msg);
                }
        }

        public static void verr(java.lang.String msg){
                if(verbosity){
                        err(msg);
                }
        }

        public static void vinfo(java.lang.String msg){
                if(verbosity){
                        info(msg);
                }
        }

        public static String color(java.lang.String msg, int Level){
                String res = "";
                switch (Level) {
                        case ERROR:
                                res = ANSI_RED + msg + ANSI_RESET;
                                break;

                        case DEBUG:
                                res = ANSI_BLUE + msg + ANSI_RESET;
                                break;
                        
                        case LOG:
                                res = ANSI_GREEN + msg + ANSI_RESET;
                                break;

                        case ORANGE:
                                res = ANSI_BG_YELLOW + msg + ANSI_RESET;
                                break;

                        default:
                                res = msg;
                                break;
                        
                }
                return res;
        }

        public static void setVerbosity(boolean verbose)
        {
                verbosity = verbose;
        }
}
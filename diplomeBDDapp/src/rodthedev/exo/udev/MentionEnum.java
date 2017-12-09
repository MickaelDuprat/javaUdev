package rodthedev.exo.udev;

public enum MentionEnum {
    
    PASSABLE("passable"),
    BIEN("bien"),
    TRESBIEN("très bien"),
    EXCELLENT("excellent"),
    PASDEMENTION("pas de mention"),
    ;
    
    private String mention;
    
    private MentionEnum(String mention){
        this.mention = mention;
    }
    
    public String toString(){
       return ("Mention " + this.mention);
    }
      
}

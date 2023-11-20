package utils;

import soc.TikTok;

import java.io.*;

public class UserScanner {
    private File filename;
    private File fileout;
    private BufferedReader bufferedReader;
    private BufferedWriter notFoundWriter;
    private BufferedReader scanReader;
    private int count;
    private String creator;
    private int totalCount;
    private TikTok tikTok;
    private LinkFilterChain filterChain;
    private FilterLinks filterLinks;


    public UserScanner(File filename, File fileout, TikTok tikTok, LinkFilterChain filterChain, FilterLinks filterLinks) throws FileNotFoundException {
        this.filename = filename;
        this.tikTok = tikTok;
        this.fileout = fileout;
        this.filterLinks = filterLinks;
        this.filterChain = filterChain;
    }


    public void setFilename(File filename) throws FileNotFoundException {
        this.filename = filename;
        bufferedReader = new BufferedReader(new FileReader(filename));
    }

    public void setFileout(File fileout) throws IOException {
        this.fileout = fileout;
        notFoundWriter = new BufferedWriter(new FileWriter(fileout));
    }

    public void scanBioLinks(){
        try{
            //reading count of creators inside users.txt
            while((creator=bufferedReader.readLine()) != null){
                totalCount++;
            }

            bufferedReader.close();
            bufferedReader = new BufferedReader(new FileReader(filename));


            while((creator=bufferedReader.readLine()) != null){
                System.out.println(ColorUtils.GREEN + String.format("[%s/%s]", count, totalCount) + ColorUtils.RESET);
                System.out.println(ColorUtils.BLUE + "[" + creator + "] scrapping..." + ColorUtils.RESET);
                //insert username into filterChain
                filterChain.setUsername(creator);
                tikTok.parseDescription(tikTok.getProfile(), creator, false);
                //and then put filterChain into parseBioLink
                tikTok.parseBioLink(tikTok.getProfile() + creator, filterChain, creator, false);
                count++;
            }

        }catch (Exception e){

        }finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }


            }catch (Exception e){

            }
            //result here
            System.out.println(ColorUtils.GREEN + "[Done:  " + count + "/" + totalCount + "]" + ColorUtils.RESET);
        }

    }

    public void start(){
        try{
            //reading count of creators inside users.txt
            while((creator=bufferedReader.readLine()) != null){
                totalCount++;
            }

            bufferedReader.close();
            scanReader = new BufferedReader(new FileReader(filename));

            int lineLength = 32;
            while((creator=scanReader.readLine()) != null){
                System.out.println(ColorUtils.GREEN + String.format("[%s/%s]", count, totalCount) + ColorUtils.RESET);
                System.out.print(ColorUtils.BLUE + "[" + creator + "] scrapping..." + ColorUtils.RESET);
                //insert username into filterChain
                filterChain.setUsername(creator);
                tikTok.parseDescription(tikTok.getProfile(), creator, false);
                if(tikTok.getFailureAcc()){
                    notFoundWriter.write(creator + "\n");
                    System.out.print("\r" + " ".repeat(lineLength) + "\r" + ColorUtils.RED + "[" + creator + "] is not found!" + ColorUtils.RESET);
                }else{
                    System.out.print("\r" + " ".repeat(lineLength) + "\r" + ColorUtils.YELLOW + "[" + creator + "] done!" + ColorUtils.RESET);
                }

                System.out.println();
                //and then put filterChain into parseBioLink
                //tikTok.parseBioLink(tikTok.getProfile() + creator, filterChain, creator, false);
                count++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(bufferedReader != null){
                    bufferedReader.close();
                }

                if(notFoundWriter != null){
                    notFoundWriter.close();
                }


            }catch (Exception e){

            }
            //result here
            System.out.println(ColorUtils.GREEN + "[Done:  " + count + "/" + totalCount + "]" + ColorUtils.RESET);
        }
    }

    public int countCreators(){
        return count;
    }

}

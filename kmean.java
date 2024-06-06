class KMean{

/*    void KMeans(){
        number_of_data = 4;
    }
*/

    private void InitializeData(){
       data = new float[number_of_data][];
       for(int i=0;i<number_of_data;i++)
            data[i] = new float[data_length];

       data[0][0] = 1;
       data[0][1] = 1;
       data[1][0] = 2;
       data[1][1] = 1;
       data[2][0] = 4;
       data[2][1] = 3;
       data[3][0] = 5;
       data[3][1] = 4;
    }

    private void InitializeCluster(){
       centroids = new float[number_of_cluster][];

       for(int i=0;i<number_of_cluster;i++)
            centroids[i] = new float[2];

       //inisialisasi centroid awal diambil dari data paling awal
       //karena disini ada 2 cluster maka inisialisasi centroid0 = data0, centroid1 = data1

       centroids[0][0] = data[0][0];
       centroids[0][1] = data[0][1];
       centroids[1][0] = data[1][0];
       centroids[1][1] = data[1][1];

       //System.out.println("centroid[1,0]:" + centroids[1][0]);
       //System.out.println("centroid[1,1]:" + centroids[1][1]);

        distance_matrix = new float[number_of_cluster][];

        for(int i=0;i<number_of_cluster;i++)
            distance_matrix[i] = new float[number_of_data];

    }

    private void ComputeDistance(){
        //sepertinya masalah ada disini
        float temp,temp2;

        //hitung jarak tiap-tiap pola terhadap masing-masing centroid
        for(int i=0;i<number_of_cluster;i++){
            for(int j=0;j<number_of_data;j++){
                temp2 = 0;
                for(int k=0;k<data_length;k++){
                    temp = (float) Math.pow((centroids[i][k] - data[j][k]),2);
                    temp2 += temp;
                }
                temp = (float)Math.sqrt(temp2);
                distance_matrix[i][j] =temp;
                System.out.println("d="+temp);
            }
            System.out.println("-");
        }
        System.out.println("---");
    }

    private void Grouping(){
        group = new int[number_of_data];
        number_of_data_in_a_group = new int[number_of_cluster];

        for(int i=0;i<number_of_data;i++){
            if(distance_matrix[0][i] < distance_matrix[1][i]){
                group[i] = 0;
                number_of_data_in_a_group[group[i]] += 1;
            }
            else{
                group[i] = 1;
                number_of_data_in_a_group[group[i]] += 1;
            }
            System.out.println("d[" + distance_matrix[0][i] + "][" + distance_matrix[1][i] + "] group:" + group[i]);
        }
        System.out.println("");
        System.out.println("data di group 0=" + number_of_data_in_a_group[0]);
        System.out.println("data di group 1=" + number_of_data_in_a_group[1]);
        System.out.println("");

        data_index_in_group = new int[number_of_cluster][];
        for(int i=0;i<number_of_cluster;i++){
           data_index_in_group[i] = new int[number_of_data_in_a_group[i]];
        }

        for(int i=0;i<number_of_cluster;i++){
            int index = 0;
            for(int j=0;j<number_of_data;j++){
                if(group[j]==i){
                    data_index_in_group[i][index] = j;
                    System.out.println("data:"+ data_index_in_group[i][index]);
                    index++;
                }
            }
            System.out.println("--");
        }
    }

    private void UpdateCentroid(){
        float[][][] avg_data = new float[number_of_cluster][][];
        float[][] new_centroids = new float[number_of_cluster][data_length];

        for(int i=0;i<number_of_cluster;i++){
                avg_data[i] = new float[number_of_data_in_a_group[i]][];
        }
        for(int i=0;i<number_of_cluster;i++){
            for(int j=0;j<number_of_data_in_a_group[i];j++){
                avg_data[i][j] = new float[data_length];
            }
        }

        float[][] temp = new float[number_of_cluster][data_length];
        for(int i=0;i<number_of_cluster;i++){
            for(int j=0;j<number_of_data_in_a_group[i];j++){
                for(int k=0;k<data_length;k++){
                    avg_data[i][j][k] = data[data_index_in_group[i][j]][k];
                    System.out.println("avg-data[" + j + "," + k + "]=" + avg_data[i][j][k]);
                    temp[i][k] += avg_data[i][j][k];
                    if(j==number_of_data_in_a_group[i]-1){
                        new_centroids[i][k] = temp[i][k] / number_of_data_in_a_group[i];
                        System.out.println("new-centroids:" + new_centroids[i][k]);
                    }
                }
                System.out.println("-");//"temp[i][k]:"+temp[i][index-1]);
            }
            System.out.println("-*-");
        }

        for(int i=0;i<number_of_cluster;i++){
            for(int j=0;j<data_length;j++){
                centroids[i][j] = new_centroids[i][j];
                System.out.println("centroid[" + i + "][" + j + "]=" + centroids[i][j]);
            }
        }
    }

    public void RunKMean(){
        InitializeData();
        InitializeCluster();
        for(int i=0;i<number_of_iteration;i++){
        	System.out.println("Iteration[" + i + "]:");
        	ComputeDistance();
            Grouping();
            UpdateCentroid();
        }
    }

    public static void main(String[] args){
        KMean kmean = new KMean();
        kmean.number_of_cluster = 2;
        kmean.number_of_data = 4;
        kmean.data_length = 2;
        kmean.number_of_iteration = 3;
        kmean.RunKMean();
    }

    //variables
    public int number_of_cluster;
    private int number_of_data;
    private int data_length;
    private float[][] centroids;
    private float[][] data;
    private float[][] distance_matrix;
    private int[] group;
    private int[] number_of_data_in_a_group;
    private int[][] data_index_in_group;
    private int number_of_iteration;
}
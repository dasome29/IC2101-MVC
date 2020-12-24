using System.Collections;
using UnityEngine;
using System.Collections.Generic;
using System;
using System.IO;
using System.Net.Sockets;
using System.Threading;

public class Pixel {
    private int i, j;
    private float value;
    private SpriteRenderer s;
    private GameObject g;
    public Pixel(int I, int J, float Value){
        i = I;
        j = J;
        value = Value;

        g = new GameObject();
        s = g.AddComponent<SpriteRenderer>();
    }

    public void Spawn(int Horizontal, int Vertical, Sprite sprite){
        g.transform.position = new Vector3(i - (Horizontal - 0.5f), j - (Vertical - 0.5f));
        s.sprite = sprite;
        s.color = new Color(value, value, value);
    }

    public void ChangeColor(string value){
        Color color= new Color();
        ColorUtility.TryParseHtmlString (value, out color);
        s.color = color;
    }
}

public class Main : MonoBehaviour
{
    public Pixel[,] Grid;
    int Vertical, Horizontal;
    public int Columns, Rows;
    public Sprite sprite;
    private string[] colors = {"#61A0AF", "#96C9DC", "#F06C9B", "#CBF3F0", "#04E762", "#DC0073", "#008BF8", "#6C464F", "#9FA4C4", 
    "#B3CDD1", "#C7F0BD", "#20063B", "#FFD166", "#8F8073", "#A682FF", "#B7FDFE"};
    
    // Start is called before the first frame update
    void Start()
    {
        Vertical = (int) Camera.main.orthographicSize;
        Horizontal = Vertical * (Screen.width / Screen.height);
        Grid = new Pixel[Columns, Rows];
        for (int i = 0; i < Columns; i++){
            for (int j = 0; j < Rows; j++){
                Grid[i, j] = new Pixel(i, j, 0.0f);
                Grid[i, j].Spawn(Horizontal, Vertical, sprite);
            }
        }
        SetupServer();
    }

    
    

    // Update is called once per frame
    void Update()
    {
        
    }
    
private Socket _clientSocket = new Socket(AddressFamily.InterNetwork,SocketType.Stream,ProtocolType.Tcp);
private byte[] _recieveBuffer = new byte[8142];

void OnApplicationQuit()
{
    _clientSocket.Close ();
}

private void SetupServer()
{
    try
    {
        _clientSocket.Connect(new IPEndPoint(IPAddress.Loopback,7777));
    }
    catch(SocketException ex)
    {
        Debug.Log(ex.Message);
    }

    _clientSocket.BeginReceive(_recieveBuffer,0,_recieveBuffer.Length,SocketFlags.None,new AsyncCallback(ReceiveCallback),null);

}


private void ReceiveCallback(IAsyncResult AR)
{
    //Check how much bytes are recieved and call EndRecieve to finalize handshake
    int recieved = _clientSocket.EndReceive(AR);

    if(recieved <= 0)
        return;

    //Copy the recieved data into new buffer , to avoid null bytes
    byte[] recData = new byte[recieved];
    Buffer.BlockCopy(_recieveBuffer,0,recData,0,recieved);

    //Process data here the way you want , all your bytes will be stored in recData
    Debug.Log(System.Text.Encoding.Default.GetString(_recieveBuffer));
    //SendData (System.Text.Encoding.Default.GetBytes ("ping"));

    //Start receiving again
    _clientSocket.BeginReceive(_recieveBuffer,0,_recieveBuffer.Length,SocketFlags.None,new AsyncCallback(ReceiveCallback),null);
}

private void SendData(byte[] data)
{
    SocketAsyncEventArgs socketAsyncData = new SocketAsyncEventArgs();
    socketAsyncData.SetBuffer(data,0,data.Length);
    _clientSocket.SendAsync(socketAsyncData);
}
}




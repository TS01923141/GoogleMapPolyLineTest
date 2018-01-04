# GoogleMapPolyLineTest
First entry origin and destination, after click button. app will mark start&amp;end position and draw path.

## 執行流程 ##
    onMapReady:檢查權限 
        check Permission
    sendRequestTest:取得起訖點 or sendRequest:取得輸入的起訖點       
        get start&end position        
    directionFinder.execute:執行onDirectionFinderStart                    
        excute onDirectionFinderStart and fetchData 
    onDirectionFinderStart:檢查是否有舊的線，有的話刪除               
        delete old mark and polyLine    
    directionFinder.fetchData:取得JSON                             
        get Json    
    directionFinder.parseJson:解析JSON，分別取得PolyLine跟起訖點     
        parse Json and excute   
    onDirectionFinderSuccess:畫線 
        draw polyLine   
    markPoint:畫起訖點  
        mark start&end point    

## Thread ##
    onMapReady                   mainThread         
    sendRequestTest              mainThread
    directionFinder.execute      mainThread
    onDirectionFinderStart       mainThread
    directionFinder.fetchData    Scheduler.io(RxCachedThreadScheduler-1)
    directionFinder.parseJson    Scheduler.io(RxCachedThreadScheduler-1)
    onResponse/onFailure         Executors.newSingleThreadExecutor()(pool-3-thread-1)
    onDirectionFinderSuccess     mainThread
    markPoint                    mainThread
